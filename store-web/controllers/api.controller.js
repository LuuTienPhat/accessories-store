const { default: axios } = require("axios");

const static = require("../static");

exports.returnProductImage = async (req, res, next) => {
  const productId = req.params.productId;
  const fileName = req.params.fileName;

  //const imageData = null;

  await axios
    .get(`${static.API_URL}/files/products/${productId}/${fileName}`, {
      responseType: "arraybuffer",
    })
    .then((response) => {
      res.status(200).end(response.data);
    })
    .catch((err) => console.log(err));
};
