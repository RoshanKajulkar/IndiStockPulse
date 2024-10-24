import { Request, Response } from 'express';
import pool from '../utils/db';
import dayjs from 'dayjs';

export const getSymbols = async (req: Request, res: Response) => {
    const { pattern } = req.query;

    try {
        let query = 'SELECT * FROM symbols';
        const values: string[] = [];

        if (pattern) {
            query += ' WHERE LOWER(symbol) LIKE LOWER($1)';
            values.push(`%${pattern}%`);
        }

        const result = await pool.query(query, values);
        const symbols = result.rows.map(s => s.symbol);

        res.status(200).json({
            count: symbols.length,
            symbols,
        });
        return;
    } catch (error) {
        res.status(500).json({ message: 'Internal server error' });
        return;
    }
};

export const getStockData = async (req: Request, res: Response) => {
    const { symbol, range_from, range_to } = req.body;

    if (!symbol || !range_from || !range_to) {
        res.status(400).json({
            s: 'error',
            message: 'All fields (symbol, range_from, range_to) are required.',
        });
        return;
    }

    try {
        const fromDate = dayjs(range_from);
        const toDate = dayjs(range_to);

        if (!fromDate.isValid() || !toDate.isValid()) {
            res.status(400).json({
                s: 'error',
                message: 'Invalid date format. Use yyyy-mm-dd.',
            });
            return;
        }

        const dateDifference = toDate.diff(fromDate, 'day');
        if (dateDifference > 100) {
            res.status(400).json({
                s: 'error',
                message:
                    'Date range exceeds the maximum allowed limit of 100 days.',
            });
            return;
        }

        const fromEpoch = fromDate.unix();
        const toEpoch = toDate.unix();

        const symbolResult = await pool.query(
            'SELECT id FROM symbols WHERE symbol = $1',
            [symbol]
        );

        if (symbolResult.rows.length === 0) {
            res.status(404).json({
                s: 'error',
                message: `Symbol ${symbol} not found.`,
            });
            return;
        }

        const symbolId = symbolResult.rows[0].id;

        const query = `
            SELECT * FROM stock_data_15min 
            WHERE symbol_id = $1 
            AND current_epoch_time BETWEEN $2 AND $3
            ORDER BY current_epoch_time ASC
        `;
        const values = [symbolId, fromEpoch, toEpoch];

        const stockDataResult = await pool.query(query, values);

        if (stockDataResult.rows.length === 0) {
            res.status(404).json({
                s: 'error',
                message:
                    'No stock data found for the given symbol and date range.',
            });
            return;
        }

        const candles = stockDataResult.rows.map((row: any) => [
            row.current_epoch_time,
            Number.parseFloat(row.open),
            Number.parseFloat(row.high),
            Number.parseFloat(row.low),
            Number.parseFloat(row.close),
        ]);

        res.status(200).json({
            s: 'ok',
            candles,
        });

        return;
    } catch (error) {
        res.status(500).json({ s: 'error', message: 'Internal server error' });
        return;
    }
};
