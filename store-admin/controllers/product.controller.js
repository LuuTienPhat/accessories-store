const { default: axios } = require("axios");
const qs = require("qs");
const static = require("../static");
const staticFunc = require("../staticFunction");
var FormData = require("form-data");
const fs = require("fs");
// const multer = require('multer');
// const upload = multer();

const originalUrl = `${static.ADMIN_PATH}${static.PRODUCT_PATH}`;

exports.returnProductPage = async (req, res, next) => {
  let { p } = req.query;
  let { s } = req.query;

  let products = [];
  let notyfOptions = null;
  let notyfOptionsFlash = null;
  let count = 58000;
  let perPage = 10;
  let offset = 0;
  let limit = 10;
  let total = 0;
  let search = "";
  let apiUrl = "";

  if (p != null) {
    offset = parseInt(p) - 1;
  }
  apiUrl = `${static.API_URL}${static.API_PRODUCT_PATH}?offset=${offset}&limit=${limit}`;

  if (s != null) {
    search = s;
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

  res.render(`${static.VIEWS_PAGE_DIR}${static.PRODUCT_DIR}${static.PRODUCT_VIEW}`, {
    title: "Products",
    breadcrumb: staticFunc.initBreadcrumbOptions("Products", "Management", false),
    products: products,
    notyfOptions: notyfOptions,
    originalUrl: originalUrl,
    pagination: staticFunc.initPagination(perPage, total, count, Math.ceil(total / perPage), offset + 1),
    keyword: s != null ? search : "",
    modal: { content: static.DELETE_PRODUCT_QUESTION },
  });
};

exports.returnAddProductPage = async (req, res, next) => {
  let categories = [];
  let notyfOptions = await req.consumeFlash("notyfOptions");
  let data = await req.consumeFlash("data");

  await axios
    .get(`${static.API_URL}${static.API_CATEGORY_PATH}`)
    .then((res) => res.data)
    .then((data) => {
      if (data.statusCode == 200) {
        categories = data.data;
      }
    });

  res.render(`${static.VIEWS_PAGE_DIR}${static.PRODUCT_DIR}${static.ADD_PRODUCT_VIEW}`, {
    title: "Add Product",
    breadcrumb: staticFunc.initBreadcrumbOptions("Products", "New", true),
    notyfOptions: notyfOptions[0],
    originalUrl: originalUrl,
    categories: categories,
    data: data[0] ? JSON.parse(data[0]) : null,
  });
};

exports.returnProductDetailPage = async (req, res, next) => {
  const id = req.params.id;
  let product = null;

  await axios
    .get(`${static.API_URL}${static.API_PRODUCT_PATH}/${id}`)
    .then((res) => res.data)
    .then((data) => {
      product = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}${static.PRODUCT_DIR}${static.PRODUCT_DETAIL_VIEW}`, {
        title: product.name,
        breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.id, true),
        product: product,
        notyfOptions: null,
        originalUrl: originalUrl,
        modal: { content: static.DELETE_PRODUCT_QUESTION },
      });
    })
    .catch((err) => console.log(err));
};

exports.returnEditProductPage = async (req, res, next) => {
  const id = req.params.id;
  let product = null;

  let categories = [];

  await axios
    .get(`${static.API_URL}${static.API_CATEGORY_PATH}`)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        categories = data.data;
      }
    });

  await axios
    .get(`${static.API_URL}${static.API_PRODUCT_PATH}/${id}`)
    .then((res) => res.data)
    .then((data) => {
      product = data.data;

      res.render(`${static.VIEWS_PAGE_DIR}${static.PRODUCT_DIR}${static.EDIT_PRODUCT_VIEW}`, {
        title: product.name,
        breadcrumb: staticFunc.initBreadcrumbOptions("Product", product.name, true),
        product: product,
        categories: categories,
        notyfOptions: null,
        originalUrl: `${req.originalUrl}`,
      });
    })
    .catch((err) => console.log(err));
};

exports.addProduct = async (req, res, next) => {
  let notyfOptions = null;
  const { name, price, categoryId, description, unit } = req.body;
  const files = req.files;
  // console.log('form data', formData);

  // const files = req.files;

  if (!name && name.trim().length == 0 && !price && price.trim().length == 0 && !categoryId && categoryId.trim().length == 0 && !unit && unit.trim().length == 0) {
    let data = {
      name: name,
      categoryId: categoryId,
      price: price,
      unit: unit,
      description: description,
    };
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Please fill required inputs");
    await req.flash("notyfOptions", notyfOptions);
    await req.flash("data", JSON.stringify(data));
    return res.redirect("/admin/products/add");
  }

  // let formData = new FormData();
  // formData.append("file", "files[0].originalname");

  await axios
    .post(`${static.API_URL}${static.API_PRODUCT_PATH}`, {
      name: name,
      price: price,
      unit: unit,
      categoryId: categoryId,
      description: description,
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        let productId = data.data.id;

        if (files && files.length != 0) {
          for (let file of files) {
            let image = fs.createReadStream(file.path);

            var formData = new FormData();
            formData.append("file", image);
            formData.append("action", "add");
            formData.append("productId", productId);
            await axios
              .post(`${static.API_URL}/files`, formData, {
                headers: {
                  "content-type": "multipart/form-data",
                },
              })
              .catch((err) => console.log(err));
          }
        }

        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
      }

      await req.flash("notyfOptions", notyfOptions);
      return res.redirect("/admin/products");
    })
    .catch((err) => console.log(err));

  // const params = new URLSearchParams({
  //   file: files[0]
  // }).toString();

  // await axios({
  //   url: `${static.API_URL}${static.API_UPLOAD_PATH}`,
  //   method: "POST",
  //   data: formData,
  //   headers: {
  //     "Content-Type": "multipart/form-data",
  //   },
  // })
  //   .then((res) => res.data)
  //   .then((data) => {
  //     console.log(data);
  //   })
  //   .catch((err) => console.log(err));

  // await axios
  //   .post(
  //     `${static.API_URL}${static.API_UPLOAD_PATH}`,
  //     {
  //       data: formData,
  //     },
  //     {
  //       headers: { "Content-Type": "multipart/form-data" },
  //     },
  //   )
  //   .then((res) => res.data)
  //   .then((data) => {
  //     console.log(data);
  //   })
  //   .catch((err) => console.log(err));
};

exports.updateProduct = async (req, res, next) => {
  const { id } = req.params;
  const { name, price, categoryId, description, unit } = req.body;
  const files = req.files;
  let notyfOptions = !null;

  if (!name && name.trim().length == 0 && !price && price.trim().length == 0 && !categoryId && categoryId.trim().length == 0 && !unit && unit.trim().length == 0) {
    let data = {
      name: name,
      categoryId: categoryId,
      price: price,
      unit: unit,
      description: description,
    };
    notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, "Please fill required inputs");
    await req.flash("notyfOptions", notyfOptions);
    await req.flash("data", JSON.stringify(data));
    return res.redirect("/admin/products/add");
  }

  await axios
    .put(`${static.API_URL}${static.API_PRODUCT_PATH}/${id}`, {
      name: name,
      price: price,
      unit: unit,
      categoryId: categoryId,
      description: description,
    })
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);

        let productId = data.data.id;

        if (files && files.length != 0) {
          for (let file of files) {
            let image = fs.createReadStream(file.path);

            var formData = new FormData();
            formData.append("file", image);
            formData.append("action", "edit");
            formData.append("productId", productId);
            await axios
              .post(`${static.API_URL}/files`, formData, {
                headers: {
                  "content-type": "multipart/form-data",
                },
              })
              .catch((err) => console.log(err));
          }
        }
        // let images = [];

        // if (files && files.length != 0) {
        //   for (let file of files) {
        //     let image = fs.createReadStream(file.path);
        //     images.push(image);
            
        //   }

        //   if(images.length != 0) {
        //     var formData = new FormData();
        //     formData.append("files", images);
        //     formData.append("action", "edit");
        //     formData.append("productId", productId);
        //     await axios
        //       .post(`${static.API_URL}/files`, formData, {
        //         headers: {
        //           "content-type": "multipart/form-data",
        //         },
        //       })
        //       .catch((err) => console.log(err));
        //   }
        // }
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
      }

      await req.flash("notyfOptions", notyfOptions);
      res.redirect("/admin/products");
    })
    .catch((err) => console.log(err));
};

exports.deleteProduct = async (req, res, next) => {
  const { id } = req.params;
  let notyfOptions = null;

  await axios
    .delete(`${static.API_URL}${static.API_PRODUCT_PATH}/${id}`)
    .then((res) => res.data)
    .then(async (data) => {
      if (data.statusCode == 200) {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_SUCCESS, data.message);
      } else {
        notyfOptions = staticFunc.initNotyfOptions(static.NOTYF_DANGER, data.message);
      }

      await req.flash("notyfOptions", notyfOptions);
      res.redirect("/admin/products");
    })
    .catch((err) => console.log(err));
};
