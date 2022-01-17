<template>
  <div>
    <h2 id="page-heading" data-cy="StructureHeading">
      <span v-text="$t('clarisseApp.structure.home.title')" id="structure-heading">Structures</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.structure.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StructureCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-structure"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.structure.home.createLabel')"> Create a new Structure </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && structures && structures.length === 0">
      <span v-text="$t('clarisseApp.structure.home.notFound')">No structures found</span>
    </div>
    <div class="table-responsive" v-if="structures && structures.length > 0">
      <table class="table table-striped" aria-describedby="structures">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.codeStructure')">Code Structure</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.dateDeCreation')">Date De Creation</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.dateDeCloture')">Date De Cloture</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.codeAmeli')">Code Ameli</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.codeSirpas')">Code Sirpas</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.codeReprographie')">Code Reprographie</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.article')">Article</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.libelleCourt')">Libelle Court</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.libelleLong')">Libelle Long</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.ordre')">Ordre</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.publicationSurInternet')">Publication Sur Internet</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.structure.typeStructure')">Type Structure</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="structure in structures" :key="structure.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StructureView', params: { structureId: structure.id } }">{{ structure.id }}</router-link>
            </td>
            <td>{{ structure.codeStructure }}</td>
            <td>{{ structure.dateDeCreation }}</td>
            <td>{{ structure.dateDeCloture }}</td>
            <td>{{ structure.codeAmeli }}</td>
            <td>{{ structure.codeSirpas }}</td>
            <td>{{ structure.codeReprographie }}</td>
            <td>{{ structure.article }}</td>
            <td>{{ structure.libelle }}</td>
            <td>{{ structure.libelleCourt }}</td>
            <td>{{ structure.libelleLong }}</td>
            <td>{{ structure.ordre }}</td>
            <td>{{ structure.publicationSurInternet }}</td>
            <td>
              <div v-if="structure.typeStructure">
                <router-link :to="{ name: 'TypeStructureView', params: { typeStructureId: structure.typeStructure.id } }">{{
                  structure.typeStructure.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StructureView', params: { structureId: structure.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StructureEdit', params: { structureId: structure.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(structure)"
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
        ><span id="clarisseApp.structure.delete.question" data-cy="structureDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-structure-heading" v-text="$t('clarisseApp.structure.delete.question', { id: removeId })">
          Are you sure you want to delete this Structure?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-structure"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStructure()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./structure.component.ts"></script>
