import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";

export default function AdminLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("role");
    localStorage.removeItem("username");
    navigate("/login");
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-white shadow-lg flex flex-col">
        <h2 className="text-xl font-bold p-4 border-b">Admin Panel</h2>
        <nav className="flex-1 p-4 space-y-2">
          <Link to="/admin/dashboard" className="block p-2 rounded hover:bg-blue-100">
            Dashboard
          </Link>
          <Link to="/admin/users" className="block p-2 rounded hover:bg-blue-100">
            Users
          </Link>
          <Link to="/admin/plans" className="block p-2 rounded hover:bg-blue-100">
            Recharge Plans
          </Link>
          <Link to="/admin/registrations" className="block p-2 rounded hover:bg-blue-100">
            New Registrations
          </Link>
          <Link to="/admin/update-requests" className="block p-2 rounded hover:bg-blue-100">
            Update Requests
          </Link>
          <Link to="/admin/delete-requests" className="block p-2 rounded hover:bg-blue-100">
            Delete Requests
          </Link>

        </nav>
        <button
          onClick={handleLogout}
          className="m-4 bg-red-500 text-white rounded py-2 hover:bg-red-600"
        >
          Logout
        </button>
      </aside>

      {/* Content */}
      <main className="flex-1 p-6">
        <Outlet />
      </main>
    </div>
  );
}
