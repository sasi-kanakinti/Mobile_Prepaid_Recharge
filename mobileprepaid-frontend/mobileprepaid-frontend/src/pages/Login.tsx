import React from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { login } from "../api/authService";

type FormData = { email: string; password: string };

export default function Login() {
  const { register, handleSubmit } = useForm<FormData>();
  const navigate = useNavigate();
  const [error, setError] = React.useState("");

  const onSubmit = async (data: FormData) => {
    try {
      const res = await login(data);
      localStorage.setItem("accessToken", res.token);
      localStorage.setItem("role", res.role);
      localStorage.setItem("username", res.name);
      if (res.role === "ADMIN") {
        navigate("/admin/dashboard");
    } 
    else {
        navigate("/user/dashboard");
    }
    
    } catch (err: any) {
      console.error(err.response?.data);
      setError(err.response?.data?.message || "Invalid credentials");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="bg-white p-8 rounded-2xl shadow-md w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Login
        </h2>

        <input
          type="email"
          {...register("email", { required: true })}
          placeholder="Email"
          className="border w-full p-2 mb-4 rounded-md"
        />
        <input
          type="password"
          {...register("password", { required: true })}
          placeholder="Password"
          className="border w-full p-2 mb-4 rounded-md"
        />

        {error && <p className="text-red-500 mb-2">{error}</p>}

        <button
          type="submit"
          className="bg-blue-600 text-white w-full py-2 rounded-md hover:bg-blue-700 transition"
        >
          Login
        </button>
      </form>
    </div>
  );
}
