import React, { useEffect, useState } from "react";
import { getPlans, addPlan, updatePlan, deletePlan } from "../../api/plansService";

export default function AdminRechargePlans() {
  const [plans, setPlans] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [editId, setEditId] = useState<number | null>(null);

  const [form, setForm] = useState({
    name: "",
    category: "",
    price: "",
    validity: "",
  });

  const fetchPlans = async () => {
    setLoading(true);
    try {
      const data = await getPlans();
      setPlans(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPlans();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (isEdit && editId) {
        await updatePlan(editId, {
          name: form.name,
          category: form.category,
          price: Number(form.price),
          validity: form.validity,
        });
        alert("Plan updated successfully!");
      } else {
        await addPlan({
          name: form.name,
          category: form.category,
          price: Number(form.price),
          validity: form.validity,
        });
        alert("Plan added successfully!");
      }
      setShowForm(false);
      setForm({ name: "", category: "", price: "", validity: "" });
      setIsEdit(false);
      fetchPlans();
    } catch (err) {
      alert("Error saving plan");
    }
  };

  const handleEdit = (plan: any) => {
    setForm({
      name: plan.name,
      category: plan.category,
      price: plan.price,
      validity: plan.validity,
    });
    setIsEdit(true);
    setEditId(plan.id);
    setShowForm(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm("Are you sure you want to delete this plan?")) {
      await deletePlan(id);
      alert("Plan deleted successfully!");
      fetchPlans();
    }
  };

  if (loading) return <p>Loading recharge plans...</p>;

  return (
    <div>
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-2xl font-bold">Recharge Plans</h1>
        <button
          onClick={() => {
            setShowForm(true);
            setIsEdit(false);
            setForm({ name: "", category: "", price: "", validity: "" });
          }}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
        >
          + Add Plan
        </button>
      </div>

      {/* Table */}
      <table className="min-w-full bg-white border border-gray-300 rounded-lg">
        <thead>
          <tr className="bg-gray-200 text-left">
            <th className="p-3 border-b">ID</th>
            <th className="p-3 border-b">Name</th>
            <th className="p-3 border-b">Category</th>
            <th className="p-3 border-b">Price (₹)</th>
            <th className="p-3 border-b">Validity</th>
            <th className="p-3 border-b">Actions</th>
          </tr>
        </thead>
        <tbody>
          {plans.length > 0 ? (
            plans.map((p) => (
              <tr key={p.id} className="hover:bg-gray-100">
                <td className="p-3 border-b">{p.id}</td>
                <td className="p-3 border-b">{p.name}</td>
                <td className="p-3 border-b">{p.category}</td>
                <td className="p-3 border-b">{p.price}</td>
                <td className="p-3 border-b">{p.validity}</td>
                <td className="p-3 border-b space-x-2">
                  <button
                    onClick={() => handleEdit(p)}
                    className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(p.id)}
                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td className="p-3 border-b text-center" colSpan={6}>
                No recharge plans found.
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Add/Edit Form */}
      {showForm && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-xl shadow-lg w-96">
            <h2 className="text-lg font-bold mb-4">
              {isEdit ? "Edit Recharge Plan" : "Add Recharge Plan"}
            </h2>
            <form onSubmit={handleSubmit} className="space-y-3">
              <input
                type="text"
                placeholder="Name"
                className="border w-full p-2 rounded"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Category"
                className="border w-full p-2 rounded"
                value={form.category}
                onChange={(e) => setForm({ ...form, category: e.target.value })}
                required
              />
              <input
                type="number"
                placeholder="Price"
                className="border w-full p-2 rounded"
                value={form.price}
                onChange={(e) => setForm({ ...form, price: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Validity (e.g. 28 days)"
                className="border w-full p-2 rounded"
                value={form.validity}
                onChange={(e) => setForm({ ...form, validity: e.target.value })}
                required
              />
              <div className="flex justify-end space-x-2 mt-3">
                <button
                  type="button"
                  onClick={() => setShowForm(false)}
                  className="bg-gray-400 text-white px-3 py-1 rounded hover:bg-gray-500"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                >
                  {isEdit ? "Update" : "Add"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
