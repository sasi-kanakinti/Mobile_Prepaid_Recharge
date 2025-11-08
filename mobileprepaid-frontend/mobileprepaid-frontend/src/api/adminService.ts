import axiosClient from "./axiosClient";

export const getAdminStats = async () => {
  const res = await axiosClient.get("/admin/stats");
  return res.data;
};

export const getAllUsers = async () => {
  const res = await axiosClient.get("/admin/users");
  return res.data;
};

export const addUser = async (payload: {
  name: string;
  email: string;
  mobile: string;
  password: string;
}) => {
  console.log("📤 Sending payload to backend:", payload);
  const res = await axiosClient.post("/admin/users", payload);
  return res.data;
};
