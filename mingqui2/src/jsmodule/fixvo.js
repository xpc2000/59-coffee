export function fixNullToZero(vo) {
  for (let key in vo) {
    var tmp = vo[key];
    if (!tmp && typeof tmp != "undefined" && tmp != 0) {
      vo[key] = 0;
    }
  }
  return vo;
}
