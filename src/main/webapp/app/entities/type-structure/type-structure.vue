<template>
  <div>
    <h2 id="page-heading" data-cy="TypeStructureHeading">
      <span v-text="$t('clarisseApp.typeStructure.home.title')" id="type-structure-heading">Type Structures</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.typeStructure.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TypeStructureCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-type-structure"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.typeStructure.home.createLabel')"> Create a new Type Structure </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && typeStructures && typeStructures.length === 0">
      <span v-text="$t('clarisseApp.typeStructure.home.notFound')">No typeStructures found</span>
    </div>
    <div class="table-responsive" v-if="typeStructures && typeStructures.length > 0">
      <table class="table table-striped" aria-describedby="typeStructures">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.codeTypeStructure')">Code Type Structure</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.libelleCourt')">Libelle Court</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.libellePluriel')">Libelle Pluriel</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.urlComplete')">Url Complete</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.urlSimplifie')">Url Simplifie</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.typeStructure.ordre')">Ordre</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="typeStructure in typeStructures" :key="typeStructure.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TypeStructureView', params: { typeStructureId: typeStructure.id } }">{{
                typeStructure.id
              }}</router-link>
            </td>
            <td>{{ typeStructure.codeTypeStructure }}</td>
            <td>{{ typeStructure.libelle }}</td>
            <td>{{ typeStructure.libelleCourt }}</td>
            <td>{{ typeStructure.libellePluriel }}</td>
            <td>{{ typeStructure.urlComplete }}</td>
            <td>{{ typeStructure.urlSimplifie }}</td>
            <td>{{ typeStructure.ordre }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TypeStructureView', params: { typeStructureId: typeStructure.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TypeStructureEdit', params: { typeStructureId: typeStructure.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(typeStructure)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="clarisseApp.typeStructure.delete.question" data-cy="typeStructureDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-typeStructure-heading" v-text="$t('clarisseApp.typeStructure.delete.question', { id: removeId })">
          Are you sure you want to delete this Type Structure?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-typeStructure"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTypeStructure()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./type-structure.component.ts"></script>
