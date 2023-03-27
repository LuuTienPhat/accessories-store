const express = require("express");
const router = express.Router();
const categoryController = require("../controllers/category.controller");
const staticFunc = require('../staticFunction');

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

router.get("/", categoryController.returnCategoryPage);

router.get("/add", categoryController.returnAddCategoryPage);

router.post("/add", upload.single('image'), categoryController.addCategory);

router.get("/:id", categoryController.returnCategoryDetailPage);

router.get("/edit/:id", upload.single('image'), categoryController.returnEditCategoryPage);

router.post("/edit/:id", categoryController.updateCategory);

router.get("/delete/:id", categoryController.deleteCategory);

module.exports = router;
