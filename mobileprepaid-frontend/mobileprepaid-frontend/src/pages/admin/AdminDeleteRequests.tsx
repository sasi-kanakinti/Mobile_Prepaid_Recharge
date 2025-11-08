import React, { useEffect, useState } from "react";
import {
  getDeleteRequests,
  approveDeleteRequest,
  rejectDeleteRequest,
} from "../../api/requestService";

export default function AdminDeleteRequests() {
  const [requests, setRequests] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    try {
      const data = await getDeleteRequests();
      setRequests(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleApprove = async (id: number) => {
    await approveDeleteRequest(id);
    fetchData();
  };

  const handleReject = async (id: number) => {
    await rejectDeleteRequest(id);
    fetchData();
  };

  if (loading) return <p>Loading delete requests...</p>;

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Account Delete Requests</h1>
      {requests.length === 0 ? (
        <p className="text-gray-600">No pending delete requests.</p>
      ) : (
        <table className="min-w-full bg-white border border-gray-300 rounded-lg">
          <thead>
            <tr className="bg-gray-200 text-left">
              <th className="p-3 border-b">ID</th>
              <th className="p-3 border-b">User ID</th>
              <th className="p-3 border-b">Email</th>
              <th className="p-3 border-b">Reason</th>
              <th className="p-3 border-b">Actions</th>
            </tr>
          </thead>
          <tbody>
            {requests.map((r) => (
              <tr key={r.id} className="hover:bg-gray-100">
                <td className="p-3 border-b">{r.id}</td>
                <td className="p-3 border-b">{r.userId}</td>
                <td className="p-3 border-b">{r.email}</td>
                <td className="p-3 border-b">{r.reason || "-"}</td>
                <td className="p-3 border-b space-x-2">
                  <button
                    onClick={() => handleApprove(r.id)}
                    className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600"
                  >
                    Approve
                  </button>
                  <button
                    onClick={() => handleReject(r.id)}
                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                  >
                    Reject
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
