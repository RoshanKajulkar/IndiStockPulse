import { Request, Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';

const JWT_SECRET = process.env.JWT_SECRET;
const JWT_REFRESH_SECRET = process.env.JWT_REFRESH_SECRET;

if (!JWT_SECRET || !JWT_REFRESH_SECRET) {
    throw new Error(
        'JWT_SECRET or JWT_REFRESH_SECRET is not defined. Set the environment variable.'
    );
}

const isAccessToken = (decodedToken: any) => {
    return decodedToken && decodedToken.type === 'access';
};

export const authMiddleware = (
    req: Request,
    res: Response,
    next: NextFunction
) => {
    const authHeader = req.header('Authorization');

    if (!authHeader) {
        res.status(401).json({ message: 'Access denied. No token provided.' });
        return;
    }

    if (!authHeader.startsWith('Bearer ')) {
        res.status(401).json({
            message: 'Invalid token format. Must be "Bearer <token>"',
        });
        return;
    }

    const token = authHeader.split(' ')[1];

    try {
        const decoded = jwt.decode(token);

        if (!isAccessToken(decoded)) {
            res.status(403).json({
                message: 'Invalid token type. Access token required.',
            });
            return;
        }

        jwt.verify(token, JWT_SECRET);
        (req as any).user = decoded;
        next();
    } catch (error) {
        res.status(403).json({ message: 'Invalid or expired access token' });
        return;
    }
};
