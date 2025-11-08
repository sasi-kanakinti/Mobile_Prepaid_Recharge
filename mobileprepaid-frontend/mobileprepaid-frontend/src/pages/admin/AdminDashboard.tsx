import React, { useEffect, useState } from "react";
import { getAdminStats } from "../../api/adminService";

export default function AdminDashboard() {
  const [stats, setStats] = useState<any>(null);

  useEffect(() => {
    getAdminStats().then(setStats).catch(console.error);
  }, []);

  if (!stats) return <p>Loading...</p>;

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Dashboard Overview</h1>
      <div className="grid grid-cols-3 gap-6">
        <StatCard title="Total Users" value={stats.totalUsers} />
        <StatCard title="Pending Registrations" value={stats.pendingRegistrations} />
        <StatCard title="Total Plans" value={stats.totalPlans} />
        <StatCard title="Pending Deletes" value={stats.pendingDeletes} />
        <StatCard title="Update Requests" value={stats.pendingUpdateRequests} />
        <StatCard title="Total Recharges" value={stats.totalRecharges} />
      </div>
    </div>
  );
}

const StatCard = ({ title, value }: { title: string; value: number }) => (
  <div className="bg-white shadow-md p-4 rounded-lg text-center">
    <h3 className="text-lg font-semibold text-gray-700">{title}</h3>
    <p className="text-2xl font-bold text-blue-600 mt-2">{value}</p>
  </div>
);
