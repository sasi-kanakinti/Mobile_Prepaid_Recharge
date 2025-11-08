import React, { useEffect, useState } from "react";
import { getPlans, performRecharge } from "../../api/userService";

export default function UserPlans() {
  const [plans, setPlans] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const userId = localStorage.getItem("userId");

  const fetchPlans = async () => {
    const data = await getPlans();
    setPlans(data);
    setLoading(false);
  };

  useEffect(() => {
    fetchPlans();
  }, []);

  const handleRecharge = async (planId: number) => {
    await performRecharge({ userId: Number(userId), planId });
    alert("Recharge successful!");
  };

  if (loading) return <p>Loading plans...</p>;

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Available Recharge Plans</h1>
      <div className="grid grid-cols-3 gap-4">
        {plans.map((p) => (
          <div
            key={p.id}
            className="bg-white shadow p-4 rounded-lg hover:shadow-md transition"
          >
            <h3 className="font-bold text-lg">{p.name}</h3>
            <p>Category: {p.category}</p>
            <p>Price: ₹{p.price}</p>
            <p>Validity: {p.validity} days</p>
            <button
              onClick={() => handleRecharge(p.id)}
              className="mt-3 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              Recharge
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
