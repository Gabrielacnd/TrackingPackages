<template>
  <v-container>
    <!-- Tabel pentru pachete -->
    <h1>Packages</h1>
    <v-btn color="primary" @click="fetchPackages">REFRESH</v-btn>
    <v-data-table
        :headers="headers"
        :items="packages"
        class="mt-3"
        item-value="id"
        dense
    >
      <!-- Date formatate pentru Created On -->
      <template v-slot:item.formattedDate="{ item }">
        {{ item.formattedDate }}
      </template>

      <!-- Informații despre curier -->
      <template v-slot:item.courier="{ item }">
        <div>
          <strong>ID:</strong> {{ item.courier.id || "N/A" }}<br />
          <strong>Name:</strong> {{ item.courier.name || "N/A" }}<br />
          <strong>Email:</strong> {{ item.courier.email || "N/A" }}
        </div>
      </template>

      <!-- Adresă de livrare -->
      <template v-slot:item.deliveryAddress="{ item }">
        {{ item.deliveryAddress || "N/A" }}
      </template>

      <!-- Butoane de acțiune: Deliver & Assign -->
      <template v-slot:item.actions="{ item }">
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
      </template>
    </v-data-table>

    <!-- Dialog pentru asignare -->
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
              <template v-slot:append>
                <span v-if="couriers.length === 0 && !loadingCouriers">
                  No couriers available
                </span>
                <span v-if="loadingCouriers">Loading couriers...</span>
              </template>
            </v-select>
            <v-btn
                color="primary"
                type="submit"
                :disabled="!selectedCourierId || loadingAssign"
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
  name: "AssignPackage",
  data() {
    return {
      packages: [], // Lista pachetelor
      couriers: [], // Lista curierilor disponibili
      assignDialog: false, // Flag pentru dialogul de asignare
      selectedPackage: null, // Pachet selectat pentru asignare
      selectedCourierId: null, // Curier selectat
      loadingCouriers: false, // Indicator de încărcare curieri
      loadingAssign: false, // Indicator de procesare pentru asignare
      headers: [
        { text: "Package ID", value: "id" },
        { text: "Created On", value: "formattedDate" },
        { text: "Courier Details", value: "courier" },
        { text: "Delivery Address", value: "deliveryAddress" },
        { text: "Status", value: "status" },
        { text: "Actions", value: "actions" },
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
    async fetchCouriers() {
      this.loadingCouriers = true;
      try {
        const response = await axios.get(
            "http://localhost:8083/couriers/no-pending-packages"
        );
        this.couriers = response.data || [];
      } catch (error) {
        console.error("Error fetching couriers:", error);
      } finally {
        this.loadingCouriers = false;
      }
    },
    openAssignDialog(packageItem) {
      this.selectedPackage = packageItem;
      this.selectedCourierId = null; // Resetăm selecția
      this.assignDialog = true;
      this.fetchCouriers(); // Încărcăm curierii disponibili
    },
    async assignPackage() {
      this.loadingAssign = true;
      try {
        await axios.put(
            `http://localhost:8083/packages/${this.selectedPackage.id}/assign`,
            { courier_id: this.selectedCourierId }
        );
        alert(`Package ${this.selectedPackage.id} assigned successfully`);
        this.assignDialog = false;
        this.fetchPackages(); // Actualizăm lista de pachete
      } catch (error) {
        console.error("Error assigning package:", error);
        alert("Failed to assign package. Please try again.");
      } finally {
        this.loadingAssign = false;
      }
    },
    async deliverPackage(packageItem) {
      try {
        await axios.post("http://localhost:8083/package/deliver", {
          packageId: packageItem.id,
        });
        alert(`Package ${packageItem.id} delivered successfully`);
        this.fetchPackages(); // Actualizăm lista de pachete
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
