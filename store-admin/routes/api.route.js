const express = require("express");
const router = express.Router();
const apiController = require("../controllers/api.controller");

router.get("/files/products/:productId/:fileName", apiController.returnProductImage);

router.get("/chart/sales-chart", apiController.returnSalesChart);

router.get("/chart/orders-chart", apiController.returnOrdersChart);

module.exports = router;