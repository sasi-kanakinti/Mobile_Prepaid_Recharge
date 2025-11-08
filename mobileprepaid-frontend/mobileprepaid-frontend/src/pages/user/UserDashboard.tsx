import React from "react";

export default function UserDashboard() {
  const username = localStorage.getItem("username");
  const email = localStorage.getItem("email");

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Welcome, {username}</h1>
      <p className="text-gray-600 mb-6">{email}</p>

      <div className="grid grid-cols-3 gap-6">
        <Card title="Recharge Plans" link="/user/plans" />
        <Card title="Recharge History" link="/user/history" />
        <Card title="My Account" link="/user/account" />
      </div>
    </div>
  );
}

function Card({ title, link }: { title: string; link: string }) {
  return (
    <a
      href={link}
      className="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition block text-center"
    >
      <h2 className="text-lg font-semibold text-gray-700">{title}</h2>
    </a>
  );
}
