import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import PrivateRoute from "./auth/PrivateRoute";
import AdminLayout from "./layouts/AdminLayout";
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminUsers from "./pages/admin/AdminUsers";
// import AdminPlans from "./pages/admin/AdminPlans";
import AdminRegistrations from "./pages/admin/AdminRegistrations";
import AdminUpdateRequests from "./pages/admin/AdminUpdateRequests";
import AdminDeleteRequests from "./pages/admin/AdminDeleteRequests";
import UserLayout from "./layouts/UserLayout";
import UserDashboard from "./pages/user/UserDashboard";
import UserPlans from "./pages/user/UserPlans";
import UserRechargeHistory from "./pages/user/UserRechargeHistory";
import UserAccount from "./pages/user/UserAccount";
import AdminRechargePlans from "./pages/admin/AdminRechargePlans";





export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route element={<PrivateRoute />}>

          <Route path="/admin" element={<AdminLayout />}>
            <Route path="dashboard" element={<AdminDashboard />} />
            <Route path="registrations" element={<AdminRegistrations />} />
            <Route path="users" element={<AdminUsers />} />
            {/* <Route path="plans" element={<AdminPlans />} /> */}
            <Route path="plans" element={<AdminRechargePlans />} />
            <Route path="update-requests" element={<AdminUpdateRequests />} />
            <Route path="delete-requests" element={<AdminDeleteRequests />} />
          </Route>

          <Route path="/user" element={<UserLayout />}>
            <Route path="dashboard" element={<UserDashboard />} />
            <Route path="plans" element={<UserPlans />} />
            <Route path="history" element={<UserRechargeHistory />} />
            <Route path="account" element={<UserAccount />} />
          </Route>

        </Route>
      </Routes>
    </BrowserRouter>
  );
}
