import axiosClient from "./axiosClient";

// --- Account update requests ---
export const getUpdateRequests = async () => {
  const res = await axiosClient.get("/admin/update-requests");
  return res.data;
};

export const approveUpdateRequest = async (id: number) => {
  const res = await axiosClient.post(`/admin/update-requests/${id}/approve`);
  return res.data;
};

export const rejectUpdateRequest = async (id: number) => {
  const res = await axiosClient.post(`/admin/update-requests/${id}/reject`);
  return res.data;
};

// --- Delete account requests ---
export const getDeleteRequests = async () => {
  const res = await axiosClient.get("/admin/delete-requests");
  return res.data;
};

export const approveDeleteRequest = async (id: number) => {
  const res = await axiosClient.post(`/admin/delete-requests/${id}/approve`);
  return res.data;
};

export const rejectDeleteRequest = async (id: number) => {
  const res = await axiosClient.post(`/admin/delete-requests/${id}/reject`);
  return res.data;
};
