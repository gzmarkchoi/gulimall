<template>
  <div>
    <el-input placeholder="enter keyword to filter content"></el-input>
    <el-tree
      :data="menus"
      :props="defaultProps"
      node-key="catId"
      ref="menuTree"
      @node-click="nodeclick"
    ></el-tree>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  props: {},
  data() {
    return {
      menus: [],
      expandedKey: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
  computed: {},

  methods: {
    //树节点过滤
    filterNode(value, data) {
      if (!value) {
        return true;
      }
      return data.name.indexOf(value) !== -1;
    },
    getMenus() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get",
      }).then(({ data }) => {
        this.menus = data.data;
      });
    },
    nodeclick(data, node, component) {
      console.log(
        "child component of category has been clicked",
        data,
        node,
        component
      );
      // send event to parent component；
      this.$emit("tree-node-click", data, node, component);
    },
  },
  created() {
    this.getMenus();
  },
};
</script>
<style scoped>
</style>