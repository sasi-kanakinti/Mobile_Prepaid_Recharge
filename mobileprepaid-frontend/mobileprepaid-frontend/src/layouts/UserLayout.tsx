import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";

export default function UserLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("role");
    localStorage.removeItem("username");
    navigate("/login");
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      <aside className="w-64 bg-white shadow-lg flex flex-col">
        <h2 className="text-xl font-bold p-4 border-b">User Panel</h2>
        <nav className="flex-1 p-4 space-y-2">
          <Link to="/user/dashboard" className="block p-2 rounded hover:bg-blue-100">
            Dashboard
          </Link>
          <Link to="/user/plans" className="block p-2 rounded hover:bg-blue-100">
            Recharge Plans
          </Link>
          <Link to="/user/history" className="block p-2 rounded hover:bg-blue-100">
            Recharge History
          </Link>
          <Link to="/user/account" className="block p-2 rounded hover:bg-blue-100">
            My Account
          </Link>
        </nav>
        <button
          onClick={handleLogout}
          className="m-4 bg-red-500 text-white rounded py-2 hover:bg-red-600"
        >
          Logout
        </button>
      </aside>

      <main className="flex-1 p-6">
        <Outlet />
      </main>
    </div>
  );
}
