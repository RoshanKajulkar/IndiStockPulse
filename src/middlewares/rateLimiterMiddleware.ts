import { Request, Response, NextFunction } from 'express';

declare global {
    namespace Express {
        interface Request {
            user?: { id: string /* Other user properties */ };
        }
    }
}

const LIMITS = {
    PER_SECOND: 10,
    PER_MINUTE: 200,
    PER_DAY: 10000,
};

const userRequests: Record<
    string,
    {
        requestsPerSecond: number[];
        requestsPerMinute: number[];
        requestsPerDay: number[];
    }
> = {};

const cleanOldRequests = (timestamps: number[], timeWindowMs: number) => {
    const now = Date.now();
    return timestamps.filter(timestamp => now - timestamp < timeWindowMs);
};

export const rateLimiterMiddleware = (
    req: Request,
    res: Response,
    next: NextFunction
) => {
    const userId = req.user?.id;
    const now = Date.now();

    if (!userId) {
        res.status(401).json({ message: 'Unauthorized: User ID is missing.' });
        return;
    }

    if (!userRequests[userId]) {
        userRequests[userId] = {
            requestsPerSecond: [],
            requestsPerMinute: [],
            requestsPerDay: [],
        };
    }

    const userData = userRequests[userId];

    userData.requestsPerSecond = cleanOldRequests(
        userData.requestsPerSecond,
        1000
    );
    userData.requestsPerMinute = cleanOldRequests(
        userData.requestsPerMinute,
        60000
    );
    userData.requestsPerDay = cleanOldRequests(
        userData.requestsPerDay,
        86400000
    );

    if (userData.requestsPerSecond.length >= LIMITS.PER_SECOND) {
        res.status(429).json({
            message: 'Too many requests - 10 per second allowed.',
        });
        return;
    }
    if (userData.requestsPerMinute.length >= LIMITS.PER_MINUTE) {
        res.status(429).json({
            message: 'Too many requests - 200 per minute allowed.',
        });
        return;
    }
    if (userData.requestsPerDay.length >= LIMITS.PER_DAY) {
        res.status(429).json({
            message: 'Daily request limit reached - 10,000 per day allowed.',
        });
        return;
    }

    userData.requestsPerSecond.push(now);
    userData.requestsPerMinute.push(now);
    userData.requestsPerDay.push(now);

    next();
};
