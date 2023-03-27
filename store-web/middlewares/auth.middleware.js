const static = require("../static");
var cookieParser = require("cookie-parser");
const staticFunc = require("../staticFunction");
require("dotenv").config();
var axios = require("axios");

const API_SECRET_KEY = process.env.API_SECRET_KEY || "";

exports.checkAuthentication = async (req, res, next) => {
  const { accessToken, refreshToken } = req.cookies;

  if (accessToken != null) {
    await staticFunc
      .verifyToken(accessToken, API_SECRET_KEY)
      .then((result) => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        return next();
      })
      .catch((err) => {
        res.redirect("/admin/logout");
      });
  } else return res.redirect("/admin/logout");
};

exports.checkAuthorization = async (req, res, next) => {
  const { accessToken, refreshToken } = req.cookies;

  if (accessToken == null) return next();
  else {
    await staticFunc
      .verifyToken(accessToken, API_SECRET_KEY)
      .then((result) => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        return res.redirect("/admin/dashboard");
      })
      .catch((err) => {
        return res.redirect("/admin/logout");
      });
  }
};

exports.storeAuthentication = async (req, res, next) => {
  const { accessToken, refreshToken } = req.cookies;

  if (accessToken != null) {
    await staticFunc
      .verifyToken(accessToken, API_SECRET_KEY)
      .then((result) => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        return next();
      })
      .catch((err) => {
        return res.redirect("/login");
      });
  } 

  else {
    return res.redirect("/login");
  }

  // else {
  //   res.redirect("/logout");
  // }
  // else return res.redirect("/admin/logout");
};

exports.storeAuthorization = async (req, res, next) => {
  const { accessToken, refreshToken } = req.cookies;

  if (accessToken != null) {
    await staticFunc
      .verifyToken(accessToken, API_SECRET_KEY)
      .then((result) => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        return next();
      })
      .catch((err) => {
        return res.redirect("/logout");
      });
  } 

  else {
    return next();
  }

  // else {
  //   res.redirect("/logout");
  // }
  // else return res.redirect("/admin/logout");
};


exports.getCustomer = async (req, res, next) => {
  const { userId } = req.cookies;

  if(userId != null) {
    await axios
    .get(
      `${static.API_URL}${static.API_USER_PATH}/${userId}`
    )
    .then((res) => res.data)
    .then((data) => {
      let customer = data.data;
      //customer.totalQuantity = 0;
      customer.getFullName = function () {return this.lastname + " " + this.firstname};
      res.locals.customer = data.data;
      next();
    })
  }
  else {
    res.locals.customer = null;
    next();
  }
};

exports.getCategories = async (req, res, next) => {
  const apiCategoryUrl = `${static.API_URL}${static.API_CATEGORY_PATH}`;
  let categories = [];

  await axios
    .get(apiCategoryUrl)
    .then((res) => res.data)
    .then((data) => {
      categories = data.data;

      res.locals.categories = categories;
      next();
    })
};

exports.getRecentViewProducts = async (req, res, next) => {
  // const apiCartUrl = `${static.API_URL}${static.API_CART_PATH}`;
  // let carts = [];

  // await axios
  //   .get(apiCartUrl)
  //   .then((res) => res.data)
  //   .then((data) => {
  //     carts = data.data;

  //     res.locals.carts = carts;
  //     next();
  //   })

  let listRecentViewProducts = req.app.locals.listRecentViewProducts;

  if(!listRecentViewProducts) {
    listRecentViewProducts = [];
  }

  req.app.locals.listRecentViewProducts = listRecentViewProducts;

  return next();
};

exports.getRecentSearch = async (req, res, next) => {
  let listRecentSearch = req.app.locals.listRecentSearch;

  if(!listRecentSearch) {
    listRecentSearch = [];
  }
  
  req.app.locals.listRecentSearch = listRecentSearch;

  return next();
};

exports.getOriginalUrl = async(req, res, next) => {
  //res.locals.originalUrl = req.originalUrl;
  req.app.locals.originalUrl = req.originalUrl;
  return next();
}

