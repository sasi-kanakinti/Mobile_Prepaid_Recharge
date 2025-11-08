import React, { useEffect, useState } from "react";
// import { getAllPlans } from "../../api/plansService";

export default function AdminPlans() {
  const [plans, setPlans] = useState<any[]>([]);

  // useEffect(() => {
  //   getAllPlans().then(setPlans).catch(console.error);
  // }, []);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Recharge Plans</h1>
      <table className="min-w-full bg-white border border-gray-300 rounded-lg">
        <thead>
          <tr className="bg-gray-200 text-left">
            <th className="p-3 border-b">ID</th>
            <th className="p-3 border-b">Name</th>
            <th className="p-3 border-b">Category</th>
            <th className="p-3 border-b">Price</th>
            <th className="p-3 border-b">Validity</th>
          </tr>
        </thead>
        <tbody>
          {plans.map((p) => (
            <tr key={p.id} className="hover:bg-gray-100">
              <td className="p-3 border-b">{p.id}</td>
              <td className="p-3 border-b">{p.name}</td>
              <td className="p-3 border-b">{p.category}</td>
              <td className="p-3 border-b">₹{p.price}</td>
              <td className="p-3 border-b">{p.validity}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
