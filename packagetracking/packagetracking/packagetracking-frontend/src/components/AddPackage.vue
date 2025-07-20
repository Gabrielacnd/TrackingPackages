<template>
  <v-dialog v-model="showDialog" max-width="400px" class="bg-transparent">
    <div class="dialogClass">
      <h3>Welcome, {{ currentUser }}! Let's create a new package!</h3>

      <!-- Delivery Address -->
      <v-textarea
          label="Delivery Address"
          v-model="deliveryAddress"
      ></v-textarea>

      <!-- Status -->
      <v-text-field
          label="Status"
          v-model="status"
      ></v-text-field>

      <v-btn color="green" @click="createPackage">Create</v-btn>
      <v-btn color="red" @click="closeDialog">Cancel</v-btn>
    </div>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {
  name: "AddPackage",
  props: {
    currentUser: String,
  },
  data() {
    return {
      showDialog: false, // Controlează vizibilitatea dialogului
      deliveryAddress: "", // Adresa de livrare
      status: "NEW", // Status-ul implicit (poate fi modificat de utilizator)
    };
  },
  methods: {
    async createPackage() {
      try {
        // Creează payload-ul cu datele introduse și valorile implicite
        const payload = {
          deliveryAddress: this.deliveryAddress,
          status: this.status, // Preia statusul din input
          courier: {
            id: "1", // ID-ul implicit al curierului
          },
        };

        // Trimite datele către server
        const response = await axios.post(
            "http://localhost:8083/package",
            payload
        );
        console.log("Package created successfully:", response.data);

        // Trimite noul pachet către `PackagesView` printr-un eveniment
        this.$emit("package-created", response.data);

        // Închide dialogul
        this.closeDialog();
      } catch (error) {
        console.error("Error creating package:", error);
      }
    },
    closeDialog() {
      this.showDialog = false;

      // Resetează formularul
      this.deliveryAddress = "";
      this.status = "NEW";

      // Notifică componenta părinte că dialogul a fost închis
      this.$emit("dialog-closed");
    },
  },
};
</script>

<style scoped>
.dialogClass {
  padding: 20px;
  background-color: white;
}
</style>
