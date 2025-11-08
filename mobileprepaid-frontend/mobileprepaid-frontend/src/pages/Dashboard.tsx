import React from "react";

export default function Dashboard() {
  const role = localStorage.getItem("role");
  const username = localStorage.getItem("username");

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h1 className="text-3xl font-bold">Welcome, {username || "User"}!</h1>
      <p className="text-gray-600">Role: {role || "user"}</p>
    </div>
  );
}
