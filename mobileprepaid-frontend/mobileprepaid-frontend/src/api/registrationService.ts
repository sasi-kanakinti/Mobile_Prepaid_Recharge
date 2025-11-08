import axiosClient from "./axiosClient";

export const getPendingRegistrations = async () => {
  const res = await axiosClient.get("/admin/registration-requests");
  return res.data;
};

export const approveRegistration = async (id: number) => {
  const res = await axiosClient.post(`/admin/registration-requests/${id}/approve`);
  return res.data;
};

export const rejectRegistration = async (id: number) => {
  const res = await axiosClient.post(`/admin/registration-requests/${id}/reject`);
  return res.data;
};
