<template>
  <v-container>
    <h1>Packages</h1>
    <v-btn color="primary" @click="fetchPackages">REFRESH</v-btn>
    <v-btn color="secondary" @click="openManagersDialog">Show Managers</v-btn>

    <!-- Dialog pentru a afișa managerii -->
    <v-dialog v-model="managersDialog" max-width="600px">
      <v-card>
        <v-card-title>Managers</v-card-title>
        <v-card-text>
          <v-data-table
              :headers="managerHeaders"
              :items="managers"
              class="mt-3"
              dense
          ></v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="managersDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-data-table
        :headers="headers"
        :items="packages"
        class="mt-3"
        dense
    >
      <!-- Formatăm coloanele -->
      <template v-slot:[`item.formattedDate`]="{ item }">
        {{ item.formattedDate }}
      </template>
      <template v-slot:[`item.courier`]="{ item }">
        <div>

          <strong>Name:</strong> {{ item.courier.name || "N/A" }}<br />

        </div>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-btn
            color="success"
            small
            @click="deliverPackage(item)"
            :disabled="item.status === 'DELIVERED'"
        >
          Deliver
        </v-btn>
        <v-btn
            color="primary"
            small
            @click="openAssignDialog(item)"
            :disabled="item.status !== 'NEW'"
        >
          Assign
        </v-btn>
        <v-btn
            color="red"
            small
            @click="deletePackage(item.id)"
        >
          Delete
        </v-btn>
      </template>
    </v-data-table>

    <!-- Dialog pentru asignarea pachetului -->
    <v-dialog v-model="assignDialog" max-width="500px">
      <v-card>
        <v-card-title>Assign Package</v-card-title>
        <v-card-text>
          <v-form @submit.prevent="assignPackage">
            <v-select
                v-model="selectedCourierId"
                :items="couriers"
                item-text="name"
                item-value="id"
                label="Select Courier"
                :disabled="loadingCouriers"
                required
            >
              <template #append>
                <span v-if="couriers.length === 0 && !loadingCouriers">
                  No couriers available
                </span>
                <span v-if="loadingCouriers">Loading couriers...</span>
              </template>
            </v-select>

            <v-btn
                color="primary"
                type="submit"
                :disabled="!selectedCourierId"
            >
              Assign
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "PackagesView",
  data() {
    return {
      packages: [],
      couriers: [],
      managers: [],
      assignDialog: false,
      managersDialog: false,
      selectedPackage: null,
      selectedCourierId: null,
      loadingCouriers: false,
      headers: [
        { text: "Package ID", value: "id" },
        { text: "Created On", value: "formattedDate" },
        { text: "Courier Details", value: "courier" },
        { text: "Delivery Address", value: "deliveryAddress" },
        { text: "Status", value: "status" },
        { text: "Actions", value: "actions" },
      ],
      managerHeaders: [
        { text: "Manager Name", value: "name" },
        { text: "Delivered Count", value: "deliveredCount" },
      ],
    };
  },
  methods: {
    async fetchPackages() {
      try {
        const response = await axios.get("http://localhost:8083/package");
        this.packages = response.data.map((pkg) => ({
          ...pkg,
          courier: pkg.courier || { id: "", name: "", email: "" },
          formattedDate: new Date(pkg.createdOn).toLocaleString("ro-RO", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
          }),
        }));
      } catch (error) {
        console.error("Error fetching packages:", error);
      }
    },

    async fetchManagers() {
      try {
        const response = await axios.get(
            "http://localhost:8083/managers/delivered-packages-count"
        );
        this.managers = response.data.map((item) => ({
          name: item[0],
          deliveredCount: item[1],
        }));
      } catch (error) {
        console.error("Error fetching managers:", error);
      }
    },

    openManagersDialog() {
      this.managersDialog = true;
      this.fetchManagers();
    },

    async fetchCouriers() {
      try {
        this.loadingCouriers = true;
        const response = await axios.get(
            "http://localhost:8083/couriers/no-pending-packages"
        );
        this.couriers = response.data || [];
        console.log("Couriers fetched:", JSON.stringify(this.couriers));
      } catch (error) {
        console.error("Error fetching couriers:", error);
      } finally {
        this.loadingCouriers = false;
      }
    },

    openAssignDialog(packageItem) {
      this.selectedPackage = packageItem;
      this.selectedCourierId = null;
      this.assignDialog = true;
      this.fetchCouriers();
    },

    async assignPackage() {
      try {
        if (!this.selectedCourierId) {
          alert("Please select a courier.");
          return;
        }

        await axios.put(
            `http://localhost:8083/packages/${this.selectedPackage.id}/assign`,
            { courier_id: this.selectedCourierId }
        );

        alert(`Package ${this.selectedPackage.id} assigned successfully`);

        this.assignDialog = false;

        this.fetchPackages();

      } catch (error) {

        console.error("Error assigning package:", error);

        alert("Failed to assign package. Please try again.");

      }
    },

    async deletePackage(packageId) {
      const confirmDelete = confirm("Are you sure you want to delete this package?");
      if (!confirmDelete) return;

      try {
        await axios.delete(`http://localhost:8083/package/${packageId}`);
        alert(`Package ${packageId} deleted successfully`);
        this.fetchPackages(); // Reîncarcă lista de pachete după ștergere
      } catch (error) {
        console.error("Error deleting package:", error);
        alert("Failed to delete package. Please try again.");
      }
    },

    async deliverPackage(packageItem) {
      try {
        await axios.post("http://localhost:8083/package/deliver", {
          packageId: packageItem.id,
        });
        alert(`Package ${packageItem.id} delivered successfully`);
        this.fetchPackages();
      } catch (error) {
        console.error("Error delivering package:", error);
        alert("Failed to deliver the package.");
      }
    },
  },

  mounted() {
    this.fetchPackages();
  },
};
</script>

<style scoped>
/* Stiluri personalizate pentru componentă */
</style>
