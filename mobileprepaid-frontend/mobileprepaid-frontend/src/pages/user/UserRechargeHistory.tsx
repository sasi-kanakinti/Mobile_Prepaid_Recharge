import React, { useEffect, useState } from "react";
import { getRechargeHistory } from "../../api/userService";

export default function UserRechargeHistory() {
  const [history, setHistory] = useState<any[]>([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    getRechargeHistory(Number(userId))
      .then(setHistory)
      .catch(console.error);
  }, []);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Recharge History</h1>
      {history.length === 0 ? (
        <p className="text-gray-600">No recharge history found.</p>
      ) : (
        <table className="min-w-full bg-white border border-gray-300 rounded-lg">
          <thead>
            <tr className="bg-gray-200 text-left">
              <th className="p-3 border-b">Plan</th>
              <th className="p-3 border-b">Amount</th>
              <th className="p-3 border-b">Date</th>
            </tr>
          </thead>
          <tbody>
            {history.map((h) => (
              <tr key={h.id} className="hover:bg-gray-100">
                <td className="p-3 border-b">{h.planName}</td>
                <td className="p-3 border-b">₹{h.amount}</td>
                <td className="p-3 border-b">
                  {new Date(h.date).toLocaleDateString()}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
