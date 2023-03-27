const express = require("express");
const router = express.Router();
const productController = require("../controllers/product.controller");

/** Require multer */
const multer = require('multer');

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'uploads/')
    },
    filename: function (req, file, cb) {
      const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9)
      cb(null, file.fieldname + '-' + uniqueSuffix + ".jpg")
    }
  })

const upload = multer({ storage: storage })

router.get("/", productController.returnProductPage);

router.post("/", upload.array('images'), productController.addProduct);

router.get("/add", productController.returnAddProductPage);

router.post("/add", productController.addProduct);

router.get("/:id", productController.returnProductDetailPage);

router.get("/edit/:id", productController.returnEditProductPage);

router.post("/edit/:id",upload.array('images'), productController.updateProduct);

router.get("/delete/:id", productController.deleteProduct);

module.exports = router;