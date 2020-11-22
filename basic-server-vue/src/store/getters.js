const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.access,
  access: state => state.user.access,
  refresh: state => state.user.access,
  avatar: state => state.user.avatar,
  name: state => state.user.name
}
export default getters
