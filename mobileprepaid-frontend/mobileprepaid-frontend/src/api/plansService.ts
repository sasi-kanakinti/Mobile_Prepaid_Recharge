import axiosClient from "./axiosClient";

// Get all recharge plans
export const getPlans = async () => {
  const res = await axiosClient.get("/plans");
  return res.data;
};

// Add a new plan
export const addPlan = async (payload: {
  name: string;
  category: string;
  price: number;
  validity: string;
}) => {
  const res = await axiosClient.post("/plans", payload);
  return res.data;
};

// Update an existing plan
export const updatePlan = async (
  id: number,
  payload: {
    name?: string;
    category?: string;
    price?: number;
    validity?: string;
  }
) => {
  const res = await axiosClient.put(`/plans/${id}`, payload);
  return res.data;
};

// Delete a plan
export const deletePlan = async (id: number) => {
  const res = await axiosClient.delete(`/plans/${id}`);
  return res.data;
};
