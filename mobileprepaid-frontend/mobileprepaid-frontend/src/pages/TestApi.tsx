import React, { useEffect, useState } from "react";
import axiosClient from "../api/axiosClient";

export default function TestApi() {
  const [data, setData] = useState<any>(null);
  const [err, setErr] = useState<string|null>(null);

  useEffect(() => {
    axiosClient.get("/plans")
      .then(res => setData(res.data))
      .catch(e => {
        console.error(e);
        setErr(e?.response?.data?.message || e.message);
      });
  }, []);

  if (err) return <div>Error: {err}</div>;
  if (!data) return <div>Loading plans…</div>;

  return (
    <div>
      <h1>Plans (from backend)</h1>
      <pre style={{whiteSpace: "pre-wrap"}}>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}
