import React, { useState } from "react";
import { requestAccountUpdate, requestAccountDelete } from "../../api/userService";

export default function UserAccount() {
  const userId = Number(localStorage.getItem("userId"));
  const [update, setUpdate] = useState({ newEmail: "", newMobile: "" });
  const [reason, setReason] = useState("");

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    await requestAccountUpdate({ userId, ...update });
    alert("Account update request sent!");
    setUpdate({ newEmail: "", newMobile: "" });
  };

  const handleDelete = async (e: React.FormEvent) => {
  e.preventDefault();
  const email = localStorage.getItem("email");
  await requestAccountDelete({ userId, email: email || "", reason });
  alert("Account delete request sent!");
  setReason("");
};


  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">My Account</h1>

      {/* Update Account */}
      <div className="bg-white shadow p-6 rounded-lg mb-6">
        <h2 className="font-semibold mb-3 text-gray-700">Update Details</h2>
        <form onSubmit={handleUpdate} className="space-y-3">
          <input
            type="email"
            placeholder="New Email"
            className="border w-full p-2 rounded"
            value={update.newEmail}
            onChange={(e) => setUpdate({ ...update, newEmail: e.target.value })}
          />
          <input
            type="text"
            placeholder="New Mobile"
            className="border w-full p-2 rounded"
            value={update.newMobile}
            onChange={(e) => setUpdate({ ...update, newMobile: e.target.value })}
          />
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Submit Update Request
          </button>
        </form>
      </div>

      {/* Delete Account */}
      <div className="bg-white shadow p-6 rounded-lg">
        <h2 className="font-semibold mb-3 text-gray-700">Request Account Deletion</h2>
        <form onSubmit={handleDelete} className="space-y-3">
          <textarea
            placeholder="Reason for account deletion"
            className="border w-full p-2 rounded"
            value={reason}
            onChange={(e) => setReason(e.target.value)}
          />
          <button
            type="submit"
            className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
          >
            Submit Delete Request
          </button>
        </form>
      </div>
    </div>
  );
}
