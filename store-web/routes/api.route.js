const express = require("express");
const router = express.Router();
const apiController = require("../controllers/api.controller");

router.get("/files/product/:productId/:fileName", apiController.returnProductImage);

module.exports = router;