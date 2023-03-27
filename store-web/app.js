var createError = require("http-errors");
var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");
require("dotenv").config();
var cors = require("cors");
var helmet = require("helmet");
var sanitizeHtml = require("sanitize-html");
const { flash } = require("express-flash-message");
var session = require("express-session");
var moment = require("moment");
// var multer = require('multer');

const app = express();
const static = require("./static");
// var upload = multer();

const publicDir = path.join(__dirname, "public");
const viewsDir = path.join(__dirname, "views");

const PORT = process.env.PORT || 8000;

const apiRouter = require("./routes/api.route");

const storeRoute = require("./routes/store.route");

const authRoute = require("./routes/auth.route");

const storeController = require("./controllers/store.controller");

const auth = require("./middlewares/store.middleware");
const staticFunc = require("./staticFunction");


// view engine setup
app.set("views", viewsDir);
app.set("view engine", "ejs");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));
app.set("port", process.env.PORT || 8000);
app.use(cors());
// app.use(upload.array()); 
app.use(helmet({
  contentSecurityPolicy: false,
  crossOriginEmbedderPolicy: false,
}));
app.use(
  session({
    secret: "secret",
    resave: false,
    saveUninitialized: true,
    cookie: {
      maxAge: 1000 * 60 * 60 * 24 * 7, // 1 week
      // secure: true, // becareful set this option, check here: https://www.npmjs.com/package/express-session#cookiesecure. In local, if you set this to true, you won't receive flash as you are using `http` in local, but http is not secure
    },
  }),
);
app.use(flash({ sessionKeyName: "flashMessage" }));

const absoluteIndexPath = "/";

app.locals.pagesPath = {
  indexPagePath : "/",
  loginPagePath: "/login",
  registerPagePath: "/register",
  logOutPagePath: "/logout",
  productPagePath:"/products",
  categoryPagePath: "/categories",
  userPagePath: "/account",
  cartPagePath: "/cart",
  checkoutPagePath: "/checkout",
  forgotPasswordPagePath: "/forgot-password",
  changePasswordPagePath: "/account/change-password",
  orderHistoryPagePath: "/account/order-history",
  favoriteProductsPagePath : "/account/favorites",
  apiImagePath : `/api/files/product`
};

app.locals.NotyfType = {
  NOTYF_SUCCESS: static.NOTYF_SUCCESS,
  NOTYF_WARNING: static.NOTYF_WARNING,
  NOTYF_DANGER: static.NOTYF_DANGER,
};

app.locals.moment = moment;

app.locals.Money = staticFunc.Money;
app.use("/api", apiRouter);

app.get("/logout", storeController.logOut);

app.use(absoluteIndexPath, 
  auth.storeAuthorization,
  auth.getCustomer, 
  auth.getCategories, 
  auth.getRecentViewProducts,
  auth.getRecentSearch,
  storeRoute);

app.use(absoluteIndexPath,  
  auth.storeAuthentication, 
  auth.getCustomer, 
  auth.getCategories, 
  auth.getRecentViewProducts,
  auth.getRecentSearch,
authRoute);

//app.get(`${static.ADMIN_PATH}/logout`,logOut)

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});


// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});

app.listen(PORT, () => {
  console.log(`Example app listening on port ${PORT}`);
});
