const express = require('express');
const router = express.Router();
const authenticator = require("../middlewares/store.middleware");
const storeController = require("../controllers/store.controller");
const auth = require("../middlewares/store.middleware");

/* GET users listing. */
router.get('/', auth.getOriginalUrl, storeController.returnIndexPage); //done

router.get('/products', auth.getOriginalUrl, storeController.returnProductPage); //not done yet

router.get('/products/:id', auth.getOriginalUrl, storeController.returnProductDetailPage); //not implemented //not test

router.get('/categories/:id', auth.getOriginalUrl, storeController.returnProductsOfCatgoryPage); //not pagination

router.get('/forgot-password', auth.getOriginalUrl, storeController.returnForgotPasswordPage);

router.get('/products/', auth.getOriginalUrl, storeController.returnForgotPasswordPage);

router.post('/forgot-password', storeController.forgotPassword);

router.get('/enter-code', storeController.enterCode);

router.post('/login', storeController.logIn); //done

router.get('/login', auth.getOriginalUrl, storeController.returnLogInPage);


// router.post('/login', storeController.logIn); //done

// router.post('/login', storeController.logIn);

///router.get('/logout', storeController.logOut); //done //not test

router.get('/register', auth.getOriginalUrl, storeController.returnRegisterPage); //done

router.post('/register', storeController.register); //done, not test

module.exports = router;