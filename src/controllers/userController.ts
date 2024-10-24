import { Request, Response } from 'express';
import bcrypt from 'bcryptjs';
import pool from '../utils/db';
import { User } from '../models/user';
import dotenv from 'dotenv';
import {
    generateAccessToken,
    generateRefreshToken,
    revokeOldRefreshTokens,
    storeRefreshToken,
} from '../utils/jwt';
dotenv.config();

export const registerUser = async (req: Request, res: Response) => {
    const { username, email, password } = req.body;

    if (!username || !email || !password) {
        res.status(400).json({ message: 'All fields are required' });
        return;
    }

    try {
        const userExists = await pool.query(
            'SELECT * FROM users WHERE email = $1',
            [email]
        );

        if (userExists.rows.length > 0) {
            res.status(400).json({ message: 'User already exists' });
            return;
        }

        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);

        const newUser: User = {
            username,
            email,
            password: hashedPassword,
        };

        const result = await pool.query(
            'INSERT INTO users (username, email, password) VALUES ($1, $2, $3) RETURNING *',
            [newUser.username, newUser.email, newUser.password]
        );

        const { password: _, ...userWithoutPassword } = result.rows[0];

        res.status(201).json(userWithoutPassword);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Server error' });
    }
};

export const loginUser = async (req: Request, res: Response) => {
    const { email, password } = req.body;

    if (!email || !password) {
        res.status(400).json({ message: 'Email and password are required' });
        return;
    }

    try {
        const userResult = await pool.query(
            'SELECT * FROM users WHERE email = $1',
            [email]
        );

        if (userResult.rows.length === 0) {
            res.status(400).json({ message: 'Invalid credentials' });
            return;
        }

        const user = userResult.rows[0];

        const isMatch = await bcrypt.compare(password, user.password);

        if (!isMatch) {
            res.status(400).json({ message: 'Invalid credentials' });
            return;
        }

        await revokeOldRefreshTokens(user.id);

        const accessToken = generateAccessToken(user.id);
        const refreshToken = generateRefreshToken(user.id);

        const expiresAt = new Date(Date.now() + 15 * 24 * 60 * 60 * 1000);
        await storeRefreshToken(user.id, refreshToken, expiresAt);

        res.status(200).json({
            message: 'Login successful',
            accessToken,
            refreshToken,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Server error' });
    }
};
