import axiosClient from "./axiosClient";

// 🔹 Get all recharge plans
export const getPlans = async () => {
  const res = await axiosClient.get("/plans");
  return res.data;
};

// 🔹 Perform a recharge
export const performRecharge = async (payload: {
  userId: number;
  planId: number;
  paymentMethod?: string;
}) => {
  // the backend RechargeController expects:
  // {
  //   "userId": number,
  //   "planId": number,
  //   "paymentMethod": "UPI" | "CARD" | "CASH" | etc
  // }
  const res = await axiosClient.post("/recharge", {
    ...payload,
    paymentMethod: payload.paymentMethod || "ONLINE", // fallback default
  });
  return res.data;
};

// 🔹 Fetch recharge history for a specific user
export const getRechargeHistory = async (userId: number) => {
  const res = await axiosClient.get(`/recharge/history/${userId}`);
  return res.data;
};

// 🔹 Submit account update request
export const requestAccountUpdate = async (payload: {
  userId: number;
  newEmail?: string;
  newMobile?: string;
}) => {
  const res = await axiosClient.post("/user/requests/update", payload);
  return res.data;
};

// 🔹 Submit account delete request
export const requestAccountDelete = async (payload: {
  userId: number;
  reason: string;
  email?: string;
}) => {
  const res = await axiosClient.post("/user/requests/delete", payload);
  return res.data;
};
