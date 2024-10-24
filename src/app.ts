import express from 'express';
import dotenv from 'dotenv';
import userRoutes from './routes/userRoutes';
import authRoutes from './routes/authRoute';
import stockRoutes from './routes/stockRoutes';
import { authMiddleware } from './middlewares/authMiddleware';
import { rateLimiterMiddleware } from './middlewares/rateLimiterMiddleware';

dotenv.config();

const app = express();

const PORT = process.env.PORT || 3000;

app.use(express.json());

app.use('/api/user', userRoutes);
app.use('/api/auth', authRoutes);
app.use('/api/stocks', authMiddleware, rateLimiterMiddleware, stockRoutes);

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});