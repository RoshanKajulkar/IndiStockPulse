import jwt from 'jsonwebtoken';
import pool from '../utils/db';

const JWT_SECRET = process.env.JWT_SECRET;
const JWT_REFRESH_SECRET = process.env.JWT_REFRESH_SECRET;

if (!JWT_SECRET || !JWT_REFRESH_SECRET) {
    throw new Error(
        'JWT_SECRET or JWT_REFRESH_SECRET is not defined. Set the environment variable.'
    );
}

export const generateAccessToken = (userId: string) => {
    return jwt.sign({ id: userId, type: 'access' }, JWT_SECRET, {
        expiresIn: '1h',
    });
};

export const generateRefreshToken = (userId: string) => {
    return jwt.sign({ id: userId, type: 'refresh' }, JWT_REFRESH_SECRET, {
        expiresIn: '5d',
    });
};

export const revokeOldRefreshTokens = async (userId: string) => {
    await pool.query('DELETE FROM refresh_tokens WHERE user_id = $1', [userId]);
};

export const storeRefreshToken = async (
    userId: string,
    refreshToken: string,
    expiresAt: Date
) => {
    await pool.query(
        'INSERT INTO refresh_tokens (user_id, refresh_token, expires_at) VALUES ($1, $2, $3)',
        [userId, refreshToken, expiresAt]
    );
};
