import express from 'express';
import { refreshAccessToken } from '../controllers/authController'; // Adjust path as needed

const router = express.Router();

router.post('/refresh-token', refreshAccessToken);

export default router;
