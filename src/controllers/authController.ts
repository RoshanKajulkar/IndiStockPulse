import { Request, Response } from 'express';
import jwt from 'jsonwebtoken';
import pool from '../utils/db';
import { generateAccessToken } from '../utils/jwt';

const JWT_SECRET = process.env.JWT_SECRET;
const JWT_REFRESH_SECRET = process.env.JWT_REFRESH_SECRET;

if (!JWT_SECRET || !JWT_REFRESH_SECRET) {
    throw new Error(
        'JWT_SECRET or JWT_REFRESH_SECRET is not defined. Set the environment variable.'
    );
}

export const refreshAccessToken = async (req: Request, res: Response) => {
    const { refreshToken } = req.body;

    if (!refreshToken) {
        res.status(401).json({ message: 'Refresh token is required' });
        return;
    }

    try {
        const decoded = jwt.verify(
            refreshToken,
            JWT_REFRESH_SECRET
        ) as jwt.JwtPayload;

        const tokenResult = await pool.query(
            'SELECT * FROM refresh_tokens WHERE user_id = $1 AND refresh_token = $2',
            [decoded.id, refreshToken]
        );

        if (tokenResult.rows.length === 0) {
            res.status(403).json({ message: 'Invalid refresh token' });
            return;
        }

        const newAccessToken = generateAccessToken(decoded.id);

        res.status(200).json({ accessToken: newAccessToken });
        return;
    } catch (error) {
        res.status(403).json({ message: 'Invalid or expired refresh token' });
        return;
    }
};
