const { default: axios } = require("axios");
const qs = require("qs");
const static = require("../static");
const staticFunc = require("../staticFunction");

exports.returnDashboardPage = async (req, res, next) => {
  let stat = null;
  let count = 0;
  let mostViewedProducts = [];
  let mostFavoriteProducts = [];
  let latestOrders = [];

  let endpoints = [
    `${static.API_URL}${static.API_PRODUCT_PATH}/most-viewed`,
    `${static.API_URL}${static.API_PRODUCT_PATH}/most-favorite`,
    `${static.API_URL}${static.API_ORDER_PATH}/latest`,
    `${static.API_URL}${static.API_STAT_PATH}`
  ]

  // await axios
  //   .get(`${static.API_URL}${static.API_PRODUCT_PATH}/most-viewed`)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       mostViewedProducts = data.data;
  //     }
  //   })
  //   .catch((err) => console.log(err));

  //   await axios
  //   .get(`${static.API_URL}${static.API_PRODUCT_PATH}/most-favorite`)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       mostFavoriteProducts = data.data;
  //     }
  //   })
  //   .catch((err) => console.log(err.data));

  // await axios
  //   .get(`${static.API_URL}${static.API_ORDER_PATH}/latest`)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       latestOrders = data.data;
  //     }
  //   })
  //   .catch((err) => console.log(err));

  // await axios
  //   .get(`${static.API_URL}${static.API_STAT_PATH}`)
  //   .then((res) => res.data)
  //   .then(async (data) => {
  //     if (data.statusCode == 200) {
  //       stat = data.data;
  //     }

  //     res.render(`${static.VIEWS_PAGE_DIR}${static.DASHBOARD_DIR}/${static.dashboardView}`, {
  //       stat: stat,
  //       title: "Dashboard",
  //       mostViewedProducts: mostViewedProducts,
  //       mostFavoriteProducts: mostFavoriteProducts,
  //       latestOrders: latestOrders,
  //     });
  //   })
  //   .catch((err) => console.log(err.data));


    axios.all(endpoints.map((endpoint) => axios.get(endpoint))).then(
      axios.spread(({data: mostViewedProducts}, {data:mostFavoriteProducts}, {data:latestOrders}, {data:stat}) => {
        //console.log({ user, repos, followers, following });

        res.render(`${static.VIEWS_PAGE_DIR}${static.DASHBOARD_DIR}/${static.dashboardView}`, {
          stat: stat.data,
          title: "Dashboard",
          mostViewedProducts: mostViewedProducts.data,
          mostFavoriteProducts: mostFavoriteProducts.data,
          latestOrders: latestOrders.data,
        });
      })
    );
};

// exports.returnViewPage = (req, res, next) => {
//   res.render(`${viewsPagesDir}${dashboardDir}`, {
//     title: "Dashboard",
//   });
// };
