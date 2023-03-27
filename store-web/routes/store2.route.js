const express = require('express');
const router = express.Router();
const authenticator = require("../middlewares/auth.middleware");
const storeController = require("../controllers/store.controller");
const auth = require("../middlewares/auth.middleware");

router.get('/account', auth.getOriginalUrl, storeController.returnAccountPage); // done

router.post('/account/:id', storeController.updateAccount);

router.get('/account/change-password', auth.getOriginalUrl, storeController.returnForgotPasswordPage);

router.post('/account/change-password', auth.getOriginalUrl, storeController.returnIndexPage);

router.get('/account/order-history', auth.getOriginalUrl, storeController.returnOrderHistoryPage);

router.get('/account/favorites', auth.getOriginalUrl, storeController.returnFavoriteProductsPage);

router.get('/account/favorites/:productId', storeController.updateFavorite); 

//router.get('/account/favorites/:productId', storeController.updateFavorite); 

router.get('/cart', auth.getOriginalUrl, storeController.returnCartPage);

router.get('/cart/:productId', storeController.addToCartInit);

router.post('/cart', storeController.addToCart);

router.post('/cart/edit', storeController.updateCart);

router.get('/cart/delete/:id', storeController.deleteCart);

router.get('/checkout', auth.getOriginalUrl, storeController.returnCheckOutPage); 

router.post('/checkout', storeController.checkOut);

module.exports = router;