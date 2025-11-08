import axiosClient from "./axiosClient";

export interface LoginPayload {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  role: string;
  userId: number;
  name: string;
  email: string;
  mobile: string;
}

export const login = async (payload: LoginPayload): Promise<LoginResponse> => {
  const response = await axiosClient.post("/auth/login", payload);
  return response.data;
};
