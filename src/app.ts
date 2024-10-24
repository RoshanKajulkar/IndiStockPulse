import express, { Request, Response } from "express";
import path from "path";
import dotenv from "dotenv";
import userRoutes from "./routes/userRoutes";
import authRoutes from "./routes/authRoute";
import stockRoutes from "./routes/stockRoutes";
import { authMiddleware } from "./middlewares/authMiddleware";
import { rateLimiterMiddleware } from "./middlewares/rateLimiterMiddleware";
import cors from "cors";

dotenv.config();

const app = express();

const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.get("/", (req: Request, res: Response) => {
  res.sendFile(path.join(__dirname, "../public", "index.html"));
});

app.use("/api/user", userRoutes);
app.use("/api/auth", authRoutes);
app.use("/api/stocks", authMiddleware, rateLimiterMiddleware, stockRoutes);

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
