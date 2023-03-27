const { default: axios } = require("axios");
const qs = require("qs");
const static = require("../static");
const staticFunc = require("../staticFunction");
const fs  = require("fs");
const blob2 = require("node:buffer");

exports.returnSalesChart = async (req, res, next) => {
    const id = req.params.id;
    let salesChart = null;
  
    await axios
      .get(`${static.API_URL}${static.API_STAT_PATH}/sales-chart`)
      .then((res) => res.data)
      .then(async (data) => {
        if (data.statusCode == 200) {
          salesChart = data.data;
        }
        return res.status(200).json(salesChart);
      })
      .catch((err) => console.log(err));
  };

  exports.returnOrdersChart = async (req, res, next) => {
    const id = req.params.id;
    let ordersChart = null;
  
    await axios
      .get(`${static.API_URL}${static.API_STAT_PATH}/orders-chart`)
      .then((res) => res.data)
      .then(async (data) => {
        if (data.statusCode == 200) {
          ordersChart = data.data;
        }
        return res.status(200).json(ordersChart);
      })
      .catch((err) => console.log(err));
  };


  exports.returnProductImage = async (req, res, next) => {
    const productId = req.params.productId;
    const fileName = req.params.fileName;

    //const imageData = null;
  
    await axios
      .get(`${static.API_URL}/files/products/${productId}/${fileName}`, {
        responseType: 'arraybuffer'
      })
      .then((response) => {
        res.status(200).end(response.data);
      })
      .catch((err) => console.log(err));
  };
