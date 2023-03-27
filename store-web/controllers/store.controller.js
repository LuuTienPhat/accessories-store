const { default: axios, Axios } = require("axios");
const e = require("connect-flash");
const qs = require("qs");
const static = require("../static");
const staticFunc = require("../staticFunction");

const originalUrl = `${static.ADMIN_PATH}${static.PRODUCT_PATH}`;

// const addIn = {
//   message: null,
// };

exports.returnIndexPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let products = [];
  let categories = [];

  const apiProductUrl = `${static.API_URL}${static.API_PRODUCT_PATH}?offset=0&&limit=16`;
  const apiCategoryUrl = `${static.API_URL}${static.API_CATEGORY_PATH}`;

  const productRequest = axios.get(apiProductUrl);
  const categoryRequest = axios.get(apiCategoryUrl);

  let endpoints = [categoryRequest, productRequest];
  await axios
    .all(endpoints)
    .then(axios.spread((categoryResponse, productResponse) => [categoryResponse.data, productResponse.data]))
    .then(([categoryData, productData]) => {
      categories = categoryData.data;
      products = productData.data;

      res.render(`${static.VIEWS_PAGE_DIR}/${static.INDEX_VIEW}`, {
        title: "Spring Store 21 - Quà tặng và phụ kiện",
        products: products,
        categories: categories,
        message: null,
        // listRecentSearch: [],
        // listRecentViewProducts: [],
        listFavorite: [],
        originalUrl: originalUrl,
        customerUsername: null,
        notyfOptions: notyfOptions,
      });
    });

  // await axios
  //   .get(apiProductUrl)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       products = data.data;
  //     }
  //   })
  //   .catch((err) => console.log(err));

  // await axios
  //   .get(apiCategoryUrl)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       categories = data.data;
  //     }
  //   })
  //   .catch((err) => console.log(err));
};

//SIGN IN
exports.returnLogInPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let categories = [];
  const apiCategoryUrl = `${static.API_URL}${static.API_CATEGORY_PATH}`;

  await axios
    .get(apiCategoryUrl)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        categories = data.data;
      }
    })
    .then(() => {
      res.render("pages/store/login", {
        title: "Đăng nhập",
        notyfOptions: notyfOptions,
        categories: categories,
        message: null,
        originalUrl: originalUrl,
        listRecentSearch: [],
        listCategory: [],
        listRecentViewProducts: [],
        listFavorite: [],
        customerUsername: null,
      });
    })
    .catch((err) => console.log(err));
};

//SIGN UP
exports.returnRegisterPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let categories = [];
  const apiCategoryUrl = `${static.API_URL}${static.API_CATEGORY_PATH}`;
  await axios
    .get(apiCategoryUrl)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        categories = data.data;
      }
    })
    .then(() => {
      res.render("pages/store/register", {
        title: "Đăng ký",
        notyfOptions: notyfOptions,
        categories: categories,
        message: null,
        //originalUrl: originalUrl,
        // listRecentSearch: [],
        // listCategory: [],
        // listRecentViewProducts: [],
        listFavorite: [],
        customerUsername: null,
      });
    })
    .catch((err) => console.log(err));
};

//FORGOT PASSWORD
exports.returnForgotPasswordPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");

  res.render("pages/store/forgot-password", {
    title: "Quên mật khâủ",
    notyfOptions: notyfOptions,
    message: null,
    //originalUrl: originalUrl,
    // listRecentSearch: [],
    // listCategory: [],
    // listRecentViewProducts: [],
    listFavorite: [],
    customerUsername: null,
  });
};

exports.forgotPassword = async (req, res, next) => {
  const { username, email } = req.body;

  let notyfOptions = await req.consumeFlash("notyfOptions");

  await axios
    .post(`${static.API_URL}${static.API_CATEGORY_PATH}/${id}`)
    .then((res) => res.data)
    .then((data) => {
      if (data.statusCode == 200) {
        res.render("pages/store/forgot-password", {
          title: "Quên mật khâủ",
          notyfOptions: notyfOptions,
          message: null,
          //originalUrl: originalUrl,
          // listRecentSearch: [],
          // listCategory: [],
          // listRecentViewProducts: [],
          listFavorite: [],
          customerUsername: null,
        });
      }
    });
};

exports.enterCode = async (req, res, next) => {
  const { username, email } = req.body;

  let notyfOptions = await req.consumeFlash("notyfOptions");

  await axios
    .post(`${static.API_URL}${static.API_CATEGORY_PATH}/${id}`)
    .then((res) => res.data)
    .then((data) => {
      if (data.statusCode == 200) {
        res.render("pages/store/forgot-password", {
          title: "Quên mật khâủ",
          notyfOptions: notyfOptions,
          message: null,
          originalUrl: originalUrl,
          listRecentSearch: [],
          listCategory: [],
          listRecentViewProducts: [],
          listFavorite: [],
          customerUsername: null,
        });
      }
    });
};

//RESET PASSWORD
exports.returnResetPasswordPage = async(req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  res.render("pages/store/reset-password", {
    title: "Đặt lại mật khẩu",
    notyfOptions: notyfOptions
  });
};

//LOG IN
exports.logIn = async (req, res, next) => {
  const { username, password } = req.body;

  await axios
    .post(
      `${static.API_URL}${static.API_USER_PATH}${static.API_LOGIN_PATH}`,
      qs.stringify({
        username: username,
        password: password,
      }),
      {
        headers: {
          "content-type": "application/x-www-form-urlencoded",
        },
      },
    )
    .then((res) => res.data)
    .then(async (data) => {
      refreshToken = data.refresh_token;
      accessToken = data.access_token;

      let decodedToken = staticFunc.decodeJWT(accessToken);
      let userId = decodedToken.sub;
      let roles = decodedToken.roles;

      if (roles.includes("ROLE_CUSTOMER")) {
        let maxAge = 1000 * 60 * 60 * 24;
        let cookieOption = { maxAge: maxAge, httpOnly: true };

        res.cookie("accessToken", accessToken, cookieOption);
        res.cookie("refreshToken", accessToken, cookieOption);
        res.cookie("userId", userId, cookieOption);

        return res.redirect("/");

        // await axios.get(`${static.API_URL}${static.API_USER_PATH}${userId}`)
        // .then(res => res.data)
        // .then(data => {

        //   // let customer = data.data;
        //   // customer.getFullName = () => {
        //   //   return this.firstname + " " + this.lastname
        //   // }

        //   // res.locals.customer = customer;

        // });
      } else {
        let notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Tên đăng nhập hoặc mật khẩu không chính xác");
        await req.flash("notyfOptions", notyfOptions);
        return res.redirect("/login");
      }
    })
    .catch(async (err) => {
      let notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Tên đăng nhập hoặc mật khẩu không chính xác");
      await req.flash("notyfOptions", notyfOptions);
      return res.redirect("/login");
    });
};

exports.register = async (req, res, next) => {
  const { firstname, lastname, birthday, phone, email, gender, address, username, password, confirmPassword } = req.body;
  let notyfOptions = null;
  // const roles = [{
  //   id : roleId
  // }]

  if (confirmPassword != password) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Mật khẩu và xác nhận mật khẩu không đúng");
    await req.flash("notyfOptions", notyfOptions);
    res.redirect("/register");
  } else {
    let roleId = 3; // ROLE_CUSTOMER

    await axios
      .post(
        `${static.API_URL}${static.API_USER_PATH}`,
        {
          firstname: firstname,
          lastname: lastname,
          phone: phone,
          birthday: birthday,
          email: email,
          gender: gender,
          address: address,
          username: username,
          password: password,
          roleId: roleId,
        },
        staticFunc.axiosOptions(req),
      )
      .then((res) => res.data)
      .then(async (data) => {
        if (data.statusCode == 200) {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);
        } else {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
        }

        await req.flash("notyfOptions", notyfOptions);
        res.redirect("/register");
      })
      .catch((err) => console.log(err));
  }
};

//LOG OUT
exports.logOut = (req, res, next) => {
  res.clearCookie("accessToken");
  res.clearCookie("refreshToken");
  res.clearCookie("userId");
  res.locals.customer = null;
  res.redirect("/");
};

exports.returnProductPage = async (req, res, next) => {
  let { p } = req.query;
  let { s } = req.query;

  let products = [];
  let notyfOptions = null;
  let notyfOptionsFlash = null;
  let count = 58000;
  let perPage = 20;
  let offset = 0;
  let limit = 20;
  let total = 0;
  let search = "";
  let apiUrl = "";
  
  if (p != null) {
    offset = parseInt(p) - 1;
  }
  apiUrl = `${static.API_URL}${static.API_PRODUCT_PATH}?offset=${offset}&limit=${limit}`;

  if (s != null) {
    search = s;

    let listRecentSearch = this.getRecentSearch(req);
    listRecentSearch.push(search);
    req.app.locals.listRecentSearch = listRecentSearch;
    apiUrl = `${static.API_URL}${static.API_PRODUCT_PATH}?search=${encodeURIComponent(search)}`;
  }

  await axios.get(`${static.API_URL}${static.API_PRODUCT_PATH}${static.API_COUNT_PATH}`).then((res) => (total = res.data.data));

  await axios
    .get(apiUrl)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        products = data.data;
        count = products.length;
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
      }

      if (s != null) total = products.length;

      notyfOptionsFlash = await req.consumeFlash("notyfOptions");
      if (notyfOptionsFlash.length != 0) notyfOptions = notyfOptionsFlash[0];
    })
    .catch((err) => console.log(err));

  res.render(`${static.VIEWS_PAGE_DIR}all-products`, {
    title: "Tất cả sản phẩm",
    breadcrumb: staticFunc.initBreadcrumbOptions("Products", "Management", false),
    products: products,
    notyfOptions: notyfOptions,
    //originalUrl: originalUrl,
    pagination: staticFunc.initPagination(perPage, total, count, Math.ceil(total / perPage), offset + 1),
    keyword: s != null ? search : "",
    //modal: { content: static.DELETE_PRODUCT_QUESTION },
    message: null,
    //listRecentSearch: [],
    listFavorite: [],
  });
};

//lấy thông tin chi tiết của sản phẩm
exports.returnProductDetailPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  const id = req.params.id;
  let product = null;
  res.locals.originalUrl = req.originalUrl;

  await axios
    .get(`${static.API_URL}${static.API_PRODUCT_PATH}/${id}`)
    .then((res) => res.data)
    .then(async (data) => {
      product = data.data;

      await axios.get(`${static.API_URL}${static.API_PRODUCT_PATH}/up/${id}`);

      //addToRecentProductList(req, res, next, product);
      let listRecentViewProducts = req.app.locals.listRecentViewProducts;
      if(listRecentViewProducts.filter(item => item.id == product.id).length != 1) {
        if(listRecentViewProducts.length == 4) {
          listRecentViewProducts.shift();
          listRecentViewProducts.push(product);
        }
        else {
          listRecentViewProducts.push(product);
        }
      }

      req.app.locals.listRecentViewProducts = listRecentViewProducts;

      res.render(`${static.VIEWS_PAGE_DIR}product-detail`, {
        title: product.name,
        //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        product: product,
        notyfOptions: notyfOptions,
        originalUrl: originalUrl,
        message: null,
        //modal: { content: static.DELETE_PRODUCT_QUESTION },
        //sessionScope: null,
        //listRecentSearch: [],
        //listCategory: [],
        //listRecentViewProducts: listRecentViewProducts,
        listFavorite: [],
      });
    })
    .catch((err) => console.log(err));

    
};

// const addToRecentProductList = (req, res, next, product) => {
//   let listRecentViewProducts = res.locals.listRecentViewProducts;
//   listRecentViewProducts.push(product);
//   res.locals.listRecentViewProducts = listRecentViewProducts;
//   return listRecentViewProducts;
// };

exports.returnAccountPage = async (req, res, next) => {
  res.render(`${static.VIEWS_PAGE_DIR}user-info`, {
    title: "Tài khoản",
    //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
    //product: product,
    notyfOptions: null,
    originalUrl: originalUrl,
    message: null,
    //modal: { content: static.DELETE_PRODUCT_QUESTION },
    //sessionScope: null,
    //listRecentSearch: [],
    //listCategory: [],
    //listRecentViewProducts: [],
    listFavorite: [],
    req: req,
  });
};

exports.updateAccount = async (req, res, next) => {
  let notyfOptions = null;
  const { id } = req.params;
  let { firstname, lastname, birthday, phone, email, gender, address, roleId } = req.body;

  if ((firstname == null) | (firstname == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Tên không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/account");
  }

  if ((lastname == null) | (firstname == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Họ không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/account");
  }

  if ((phone == null) | (phone == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Điện thoại không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/account");
  }

  if ((email == null) | (email == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Email không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/account");
  }

  if ((address == null) | (address == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Địa chỉ không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/account");
  } else {
    const roles = [
      {
        id: roleId,
      },
    ];

    await axios
      .put(
        `${static.API_URL}${static.API_USER_PATH}/${id}`,
        {
          firstname: firstname,
          lastname: lastname,
          birthday: "2022-10-18",
          phone: phone,
          email: email,
          gender: "0",
          address: address,
          roles: roles,
        },
        staticFunc.axiosOptions(req),
      )
      .then((res) => res.data)
      .then(async (data) => {
        if (data.statusCode == 200) {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);
        } else {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
        }

        await req.flash("notyfOptions", notyfOptions);
        return res.redirect(`/account`);
      })
      .catch((err) => console.log(err));
  }
};

exports.returnOrderHistoryPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let orders = [];

  await axios
    .get(`${static.API_URL}${static.API_ORDER_PATH}`, {
      params: {
        userId: res.locals.customer.id,
      },
    })
    .then((res) => res.data)
    .then((data) => {
      orders = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}order-history`, {
        title: "Lịch sử đơn hàng",
        notyfOptions: notyfOptions,
        //originalUrl: originalUrl,
        message: null,
        orders: orders,
        //sessionScope: null,
        //listRecentSearch: [],
        //listCategory: [],
        //listRecentViewProducts: [],
        listFavorite: [],
        req: req,
      });
    })
    .catch((err) => console.log(err));
};

exports.returnFavoriteProductsPage = async (req, res, next) => {
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let products = [];
  let userId = res.locals.customer.id;

  //this.setOriginalUrl(req, res, next);
  res.locals.originalUrl = req.originalUrl;

  await axios
    .get(`${static.API_URL}${static.API_CART_PATH}/favorite/${userId}`)
    .then((res) => res.data)
    .then((data) => {
      if (data.statusCode == 200) {
        products = data.data;
      } 

      return res.render(`${static.VIEWS_PAGE_DIR}user-favorite`, {
        title: "Các sản phẩm yêu thích",
        //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        //product: product,
        notyfOptions: notyfOptions,
        //originalUrl: originalUrl,
        message: null,
        //carts: carts,
        products: products,
        //modal: { content: static.DELETE_PRODUCT_QUESTION },
        //sessionScope: null,
        //listRecentSearch: [],
        //listCategory: [],
        //listRecentViewProducts: [],
        //listFavorite: products,
        req: req,
      });
    })
    .catch((err) => console.log(err));

  // await axios
  //   .get(`${static.API_URL}${static}`)
  //   .then((res) => res.data)
  //   .then((data) => {
  //     product = data.data;

  //   })
  //   .catch((err) => console.log(err));
};

exports.returnCartPage = async (req, res, next) => {
  // const id = req.params.id;
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let userId = res.locals.customer.id;

  await axios
    .get(`${static.API_URL}${static.API_CART_PATH}/${userId}`)
    .then((res) => res.data)
    .then((data) => {
      carts = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}shopping-cart`, {
        title: "Giỏ hàng",
        //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        //product: product,
        notyfOptions: notyfOptions,
        //originalUrl: originalUrl,
        message: null,
        carts: carts,
        //products: res.locals.listRecentViewProducts,
        //modal: { content: static.DELETE_PRODUCT_QUESTION },
        //sessionScope: null,
        //listRecentSearch: [],
        //listCategory: [],
        //listRecentViewProducts: [],
        listFavorite: [],
        req: req,
      });
    })
    .catch((err) => console.log(err));
};

// exports.setOriginalUrl = (req, res, next) =>  {
//   return res.locals.originalUrl = req.originalUrl;
// }

exports.getOriginalUrl = (req, res, next) =>  {
  return res.locals.originalUrl;
}

exports.addToCartInit = async (req, res, next) => {
  const { productId } = req.params;
  const userId = res.locals.customer.id;
  let originalUrl = req.app.locals.originalUrl;

  await axios
    .post(`${static.API_URL}${static.API_CART_PATH}`, {
      userId: userId,
      productId: productId,
      quantity: 1,
      type: "cart",
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đã thêm vào giỏ hàng");
        await req.flash("notyfOptions", notyfOptions);
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể thêm vào giỏ hàng");
        await req.flash("notyfOptions", notyfOptions);
      }

      return res.redirect(originalUrl);

      // product = data.data.carts;

      // res.render(`${static.VIEWS_PAGE_DIR}shopping-cart`, {
      //   title: product.name,
      //   breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
      //   product: product,
      //   notyfOptions: null,
      //   originalUrl: originalUrl,
      //   message: null,
      //   modal: { content: static.DELETE_PRODUCT_QUESTION },
      //   sessionScope: null,
      //   listRecentSearch: [],
      //   listCategory: [],
      //   listRecentViewProducts: [],
      //   listFavorite: [],
      // });
    })
    .catch((err) => console.log(err));
};

exports.addToCart = async (req, res, next) => {
  const { productId, quantity } = req.body;
  let originalUrl = req.app.locals.originalUrl;
  let userId  = res.locals.customer.id

  await axios
    .post(`${static.API_URL}${static.API_CART_PATH}`, {
      userId: userId,
      productId: productId,
      quantity: quantity,
      type: 'cart'
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đã thêm vào giỏ hàng");
        await req.flash("notyfOptions", notyfOptions);
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể thêm vào giỏ hàng");
        await req.flash("notyfOptions", notyfOptions);
      }

      return res.redirect(originalUrl);

      // product = data.data.carts;

      // res.render(`${static.VIEWS_PAGE_DIR}shopping-cart`, {
      //   title: product.name,
      //   breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
      //   product: product,
      //   notyfOptions: null,
      //   originalUrl: originalUrl,
      //   message: null,
      //   modal: { content: static.DELETE_PRODUCT_QUESTION },
      //   sessionScope: null,
      //   listRecentSearch: [],
      //   listCategory: [],
      //   listRecentViewProducts: [],
      //   listFavorite: [],
      // });
    })
    .catch((err) => console.log(err));
};

exports.updateCart = async (req, res, next) => {
  //const { productId, quantity } = req.params;
  const { cartId, productId, quantity } = req.body;
  let notyfOptions = null;
  let userId = res.locals.customer.id;

  if (productId.length == 0) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không có sản phẩm trong giỏ hàng");
    await req.flash("notyfOptions", notyfOptions);
    res.redirect("/cart");
  } else if (productId.length == 1) {
    await axios
      .put(`${static.API_URL}${static.API_CART_PATH}/${cartId}`, {
        id: cartId,
        userId: userId,
        productId: productId,
        quantity: quantity,
        type: "cart",
      })
      .then((res) => res.data)
      .then(async (data) => {
        if (data.statusCode == 200) {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Cập nhập giỏ hàng thành công");
        } else {
          notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể cập nhật giỏ hàng");
        }

        await req.flash("notyfOptions", notyfOptions);
        //res.redirect("/");
      })
      .catch((err) => console.log(err));
  } else {
    let requests = productId.map(async (item, index) => {
      const request = await axios
        .put(`${static.API_URL}${static.API_CART_PATH}/${cartId[index]}`, {
          id: cartId[index],
          userId: userId,
          productId: productId[index],
          quantity: quantity[index],
          type: "cart",
        })
        .then((res) => res.data)
        .then(async (data) => {
          if (data.statusCode == 200) {
            notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Cập nhập giỏ hàng thành công");
          } else {
            notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể cập nhật giỏ hàng");
          }

          await req.flash("notyfOptions", notyfOptions);
          //res.redirect("/");
        })
        .catch((err) => console.log(err));

      return request;
    });

    await Promise.all(requests).then((value) => {
      res.redirect("/cart");
    });
  }
};

exports.deleteCart = async (req, res, next) => {
  const { id } = req.params;
  let notyfOptions = null;

  await axios
    .delete(`${static.API_URL}${static.API_CART_PATH}/${id}`)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Cập nhật giỏ hàng thành công");
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể cập nhật giỏ hàng");
      }

      await req.flash("notyfOptions", notyfOptions);
      res.redirect("/cart");
    })
    .catch((err) => console.log(err));
};

// exports.addToFavorite = async (req, res, next) => {
//   const { productId } = req.params;
//   let notyfOptions = null;

//   await axios
//     .post(`${static.API_URL}${static.API_PRODUCT_PATH}/favorite`, {
//       userId: res.locals.customer.id,
//       productId: productId,
//       type: 'like'
//     })
//     .then((res) => res.data)
//     .then(async (data) => {
//       if (data.statusCode == 200) {
//         notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đã thêm vào giỏ hàng");
//         await req.flash("notyfOptions", notyfOptions);
//       } else {
//         notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Không thể thêm vào giỏ hàng");
//         await req.flash("notyfOptions", notyfOptions);
//       }

//       return res.redirect("/");

//       // product = data.data.carts;

//       // res.render(`${static.VIEWS_PAGE_DIR}shopping-cart`, {
//       //   title: product.name,
//       //   breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
//       //   product: product,
//       //   notyfOptions: null,
//       //   originalUrl: originalUrl,
//       //   message: null,
//       //   modal: { content: static.DELETE_PRODUCT_QUESTION },
//       //   sessionScope: null,
//       //   listRecentSearch: [],
//       //   listCategory: [],
//       //   listRecentViewProducts: [],
//       //   listFavorite: [],
//       // });
//     })
//     .catch((err) => console.log(err));
// };

exports.updateFavorite = async (req, res, next) => {
  const { productId } = req.params;
  let notyfOptions = null;
  let userId = res.locals.customer.id;
  let originalUrl = req.app.locals.originalUrl;

  // console.log(res.locals.originalUrl);

  await axios
    .put(`${static.API_URL}${static.API_CART_PATH}/favorite`, {
      userId: userId,
      productId: productId,
      type: 'like'
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đã thêm vào danh sách yêu thích");
        
      } else if(data.statusCode == 202){
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đã xóa danh sách yêu thích");
      }

      await req.flash("notyfOptions", notyfOptions);
      return res.redirect(originalUrl);
    })
    .catch((err) => console.log(err));
};

exports.deletefavorite = async (req, res, next) => {
  const { productId } = req.params;
  let notyfOptions = null;

  await axios
    .delete(`${static.API_URL}${static.API_PRODUCT_PATH}/favorite/${productId}`)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
      }

      await req.flash("notyfOptions", notyfOptions);
      res.redirect(req.originalUrl);
    })
    .catch((err) => console.log(err));
};

exports.returnCheckOutPage = async (req, res, next) => {
  // const id = req.params.id;
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let userId = res.locals.customer.id;
  let carts = [];

  res.locals.originalUrl = req.originalUrl;

  await axios
    .get(`${static.API_URL}${static.API_CART_PATH}/${userId}`)
    .then((res) => res.data)
    .then((data) => {
      carts = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}check-out`, {
        title: "Thanh toán",
        //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        //product: product,
        notyfOptions: notyfOptions,
        //originalUrl: originalUrl,
        carts: carts,
        message: null,
        //modal: { content: static.DELETE_PRODUCT_QUESTION },
        //sessionScope: null,
        //listRecentSearch: [],
        //listCategory: [],
        //listRecentViewProducts: [],
        //listFavorite: [],
      });
    })
    .catch((err) => console.log(err));
};

exports.checkOut = async (req, res, next) => {
  // const id = req.params.id;
  const { cartId, productId, quantity, fullname, phone, email, address, note } = req.body;

  let notyfOptions = null;
  let userId = res.locals.customer.id;
  let orderDetails = null;

  if ((fullname == null) | (fullname == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Tên không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/checkout");
  }

  if ((phone == null) | (phone == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Số điện thoại không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/checkout");
  }

  if ((email == null) | (email == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Email không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/checkout");
  }

  if ((address == null) | (address == "")) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Địa chỉ không được để trống");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/checkout");
  }

  if (cartId.length == 0) {
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_WARNING, "Không có sản phẩm trong giỏ hàng");
    await req.flash("notyfOptions", notyfOptions);
    return res.redirect("/checkout");
  } else if (productId.length == 1) {
    orderDetails = [
      {
        cartId: cartId,
        productId: productId,
        quantity: quantity,
      },
    ];
  } else {
    orderDetails = cartId.map((item, index) => {
      return {
        cartId: cartId[index],
        productId: productId[index],
        quantity: quantity[index],
      };
    });
  }

  await axios
    .post(`${static.API_URL}${static.API_ORDER_PATH}`, {
      userId: userId,
      shipName: fullname,
      shipPhone: phone,
      shipEmail: email,
      shipAddress: address,
      shipNote: note,
      orderDetails: orderDetails,
      state: 0,
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Đặt hàng thành công");
        await req.flash("notyfOptions", notyfOptions);
        return res.redirect("/cart");
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, "Xin hãy thử lại");
        await req.flash("notyfOptions", notyfOptions);
        return res.redirect("/checkout");
      }
    })
    .catch((err) => console.log(err));
};

exports.returnProductsOfCatgoryPage = async (req, res, next) => {
  const id = req.params.id;
  let products = null;
  let notyfOptions = await req.consumeFlash("notyfOptions");

  //res.locals.originalUrl = req.originalUrl;

  await axios
    .get(`${static.API_URL}${static.API_PRODUCT_PATH}?categoryId=${id}`)
    .then((res) => res.data)
    .then((data) => {
      products = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}category-products`, {
        title: "Sản phẩm",
        //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        products: products,
        notyfOptions: notyfOptions,
        //originalUrl: originalUrl,
        message: null,
        //modal: { content: static.DELETE_PRODUCT_QUESTION },
        // sessionScope: null,
        // listRecentSearch: [],
        // listCategory: [],
        // listRecentViewProducts: [],
        listFavorite: [],
      });
    })
    .catch((err) => console.log(err));
};

exports.getRecentSearch = (req) => {
  return req.app.locals.listRecentSearch;
}

// exports.setRecentSearch = (req, s) => {
//   let listRecentSearch = this.getRecentSearch();
//   listRecentSearch.push(s);
//   req.app.locals.listRecentSearch = listRecentSearch;
//   //return req.app.locals.listRecentSearch;
// }

// exports.returnProductsOfCatgoryPage = async (req, res, next) => {
//   const id = req.params.id;
//   let products = null;

//   await axios
//     .get(`${static.API_URL}${static.API_CATEGORY_PATH}/${id}`)
//     .then((res) => res.data)
//     .then((data) => {
//       product = data.data.products;

//       res.render(`${static.VIEWS_PAGE_DIR}category-products`, {
//         title: product.name,
//         //breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
//         //product: product,
//         notyfOptions: null,
//         originalUrl: originalUrl,
//         message: null,
//         modal: { content: static.DELETE_PRODUCT_QUESTION },
//         sessionScope: null,
//         listRecentSearch: [],
//         listCategory: [],
//         listRecentViewProducts: [],
//         listFavorite: [],
//       });
//     })
//     .catch((err) => console.log(err));
// };
