import express from 'express';
import { getSymbols, getStockData } from '../controllers/stockController';

const router = express.Router();

router.get('/symbols', getSymbols);
router.post('/data', getStockData);

export default router;
