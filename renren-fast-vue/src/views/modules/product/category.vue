<template>
  <div>
    <el-switch
      v-model="draggable"
      active-text="On"
      inactive-text="Drag and Drop menu Off"
    ></el-switch>
    <el-button v-if="draggable" @click="batchSave">Save menu</el-button>
    <el-button type="danger" @click="batchDelete">Batch Delete</el-button>
    <el-tree
      :data="menus"
      :props="defaultProps"
      :expand-on-click-node="false"
      show-checkbox
      node-key="catId"
      :default-expanded-keys="expandedKey"
      :draggable="draggable"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
      ref="menuTree"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append(data)"
          >
            Append
          </el-button>
          <el-button type="text" size="mini" @click="edit(data)">
            Edit
          </el-button>
          <el-button
            v-if="node.childNodes.length == 0"
            type="text"
            size="mini"
            @click="() => remove(node, data)"
          >
            Delete
          </el-button>
        </span>
      </span></el-tree
    >
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
      :close-on-click-modal="false"
    >
      <el-form :model="category">
        <el-form-item label="Category Name">
          <el-input v-model="category.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="Category icon">
          <el-input v-model="category.icon" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="Category product unit">
          <el-input
            v-model="category.productUnit"
            auto-complete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <span>
        <el-button type="primary" @click="submitData">OK</el-button>
        <el-button @click="dialogVisible = false">Cancel</el-button>
      </span>
    </el-dialog>
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
      pCid: [],
      draggable: false,
      updatedNodes: [],
      maxLevel: 0,
      dialogTitle: "",
      dialogType: "", // edit or add
      category: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        productUnit: "",
        icon: "",
        catId: null,
      },
      dialogVisible: false,
      menus: [],
      expandedKey: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
  computed: {},
  watch: {},
  methods: {
    getMenus() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get",
      }).then(({ data }) => {
        console.log("get menus data successfully...", data.data);
        this.menus = data.data;
      });
    },
    batchDelete() {
      let catIdsToDelete = [];
      let checkedNodes = this.$refs.menuTree.getCheckedNodes();
      console.log("checked nodes: ", checkedNodes);
      for (let i = 0; i < checkedNodes.length; i++) {
        catIdsToDelete.push(checkedNodes[i].catId);
      }

      // confirm box
      this.$confirm(
        `It would delete selected menus, are you sure?`,
        "Warning",
        {
          confirmButtonText: "Yes",
          cancelButtonText: "No",
          type: "warning",
        }
      )
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(catIdsToDelete, false),
          }).then(({ data }) => {
            this.$message({
              message: "menus deleted",
              type: "success",
            });
            // refresh menus
            this.getMenus();
          });
        })
        .catch(() => {});
    },
    batchSave() {
      // set current node's new level
      console.log("updateNodes", this.updatedNodes);
      this.$http({
        url: this.$http.adornUrl("/product/category/update/sort"),
        method: "post",
        data: this.$http.adornData(this.updatedNodes, false),
      }).then(({ data }) => {
        this.$message({
          message: "menus sort order updated after drag and drop",
          type: "success",
        });
        // refresh menus
        this.getMenus();
        this.expandedKey = this.pCid;
        this.updatedNodes = [];
        this.maxLevel = 0;
        // this.pCid = 0;
      });
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log("draggingNode: ", draggingNode, dropNode, dropType);
      // 1. set current node's new parent node id
      let pCid = 0;
      let siblings = null;
      if (dropType == "before" || dropType == "after") {
        pCid =
          dropNode.parent.data.catId == undefined
            ? 0
            : dropNode.parentCid.data.catId;
        siblings = dropNode.parent.childNodes;
      } else {
        pCid = dropNode.data.catId;
        siblings = dropNode.childNodes;
      }
      // set the global pCid
      this.pCid.push(pCid);

      // 2. set current node's new order
      for (let i = 0; i < siblings.length; i++) {
        if (siblings[i].data.catId == draggingNode.data.catId) {
          let catLevel = draggingNode.level;
          if (siblings[i].level != catLevel) {
            // current node's level has changed
            catLevel = siblings[i].level;
            // update child node level
            this.udpateChildNodeLevel(siblings[i]);
          }
          this.updatedNodes.push({
            catId: siblings[i].data.catId,
            sort: i,
            parentCid: pCid,
            catLevel: catLevel,
          });
        } else {
          this.updatedNodes.push({ catId: siblings[i].data.catId, sort: i });
        }
      }
    },
    udpateChildNodeLevel(node) {
      if (node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          var cNode = node.childNodes[i].data;
          this.updatedNodes.push({
            catId: cNode.catId,
            catLevel: node.childNodes[i].level,
          });
          this.udpateChildNodeLevel(node.childNodes[i]);
        }
      }
    },
    allowDrop(draggingNode, dropNode, type) {
      //current node level
      this.countNodeLevel(draggingNode);
      let depth = Math.abs(this.maxLevel - draggingNode.level) + 1;
      console.log("Depth: ", depth);

      if ((type = "inner")) {
        return depth + dropNode.level <= 3;
      } else {
        return depth + dropNode.parent.level <= 3;
      }
    },
    countNodeLevel(node) {
      // get all children nodes, calculate the depth
      if (node.childNodes != null && node.childNodes.length > 0) {
        for (let i = 0; i < node.children.length; i++) {
          if (node.childNodes[i].level > this.maxLevel) {
            this.maxLevel = node.childNodes[i].level;
          }
          this.countNodeLevel(node.childNodes[i]);
        }
      }
    },
    edit(data) {
      console.log("edit menu item", data);
      this.dialogType = "edit";
      this.dialogVisible = true;
      this.dialogTitle = "Edit category";

      // Refresh category data from DB
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: "get",
      }).then(({ data }) => {
        console.log("data to display...", data);
        this.category.name = data.data.name;
        this.category.catId = data.data.catId;
        this.category.icon = data.data.icon;
        this.category.productUnit = data.data.productUnit;
        this.category.parentCid = data.data.parentCid;

        console.log("category...", this.category);
      });
    },
    append(data) {
      console.log("append", data);
      this.dialogType = "add";
      this.dialogVisible = true;
      this.dialogTitle = "Add category";
      this.category.parentCid = data.catId;
      this.category.catId = data.catLevel * 1 + 1;

      // Reset the values
      this.category.catId = null;
      this.category.name = "";
      this.category.icon = "";
      this.category.productUnit = "";
      this.category.sort = 0;
      this.category.showStatus = 1;
    },
    submitData() {
      if (this.dialogType == "add") {
        this.addCategory();
      }
      if (this.dialogType == "edit") {
        this.editCategory();
      }
    },
    editCategory() {
      var { catId, name, icon, productUnit } = this.category;
      /**
       * key is the same as Java Bean, ES6 structure, we could use
       * { catId, name, icon, productUnit } directly
       */
      /*
      var data = {
        catId: catId,
        name: name,
        icon: icon,
        productUnit: productUnit,
      }; */
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "post",
        data: this.$http.adornData({ catId, name, icon, productUnit }, false),
      }).then(({ data }) => {
        this.$message({
          message: "category modified",
          type: "success",
        });
        // Close dialog box
        this.dialogVisible = false;
        // Refresh menus
        this.getMenus();
        this.expandedKey = [this.category.parentCid];
      });
    },
    // Add level 3 category menu
    addCategory() {
      console.log("Add level 3 category", this.category);
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.category, false),
      }).then(({ data }) => {
        this.$message({
          message: "category saved",
          type: "success",
        });
        // Close dialog box
        this.dialogVisible = false;
        // Refresh menus
        this.getMenus();
        this.expandedKey = [this.category.parentCid];
      });
    },
    remove(node, data) {
      var ids = [data.catId];
      // confirm box
      this.$confirm(
        `It would delete ${data.name} menu, are you sure?`,
        "Warning",
        {
          confirmButtonText: "Yes",
          cancelButtonText: "No",
          type: "warning",
        }
      )
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false),
          }).then(({ data }) => {
            this.$message({
              message: "menu deleteded",
              type: "success",
            });
            this.getMenus();
            // set the default expanded menu
            this.expandedKey = [node.parent.data.catId];
          });
        })
        .catch(() => {});

      console.log("remove", node, data);
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getMenus();
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {},
  beforeMount() {},
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {},
  destroyed() {},
  activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style scoped>
</style>