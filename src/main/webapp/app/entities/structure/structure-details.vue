<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="structure">
        <h2 class="jh-entity-heading" data-cy="structureDetailsHeading">
          <span v-text="$t('clarisseApp.structure.detail.title')">Structure</span> {{ structure.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.structure.codeStructure')">Code Structure</span>
          </dt>
          <dd>
            <span>{{ structure.codeStructure }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.dateDeCreation')">Date De Creation</span>
          </dt>
          <dd>
            <span>{{ structure.dateDeCreation }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.dateDeCloture')">Date De Cloture</span>
          </dt>
          <dd>
            <span>{{ structure.dateDeCloture }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.codeAmeli')">Code Ameli</span>
          </dt>
          <dd>
            <span>{{ structure.codeAmeli }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.codeSirpas')">Code Sirpas</span>
          </dt>
          <dd>
            <span>{{ structure.codeSirpas }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.codeReprographie')">Code Reprographie</span>
          </dt>
          <dd>
            <span>{{ structure.codeReprographie }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.article')">Article</span>
          </dt>
          <dd>
            <span>{{ structure.article }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ structure.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.libelleCourt')">Libelle Court</span>
          </dt>
          <dd>
            <span>{{ structure.libelleCourt }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.libelleLong')">Libelle Long</span>
          </dt>
          <dd>
            <span>{{ structure.libelleLong }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.ordre')">Ordre</span>
          </dt>
          <dd>
            <span>{{ structure.ordre }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.publicationSurInternet')">Publication Sur Internet</span>
          </dt>
          <dd>
            <span>{{ structure.publicationSurInternet }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.structure.typeStructure')">Type Structure</span>
          </dt>
          <dd>
            <div v-if="structure.typeStructure">
              <router-link :to="{ name: 'TypeStructureView', params: { typeStructureId: structure.typeStructure.id } }">{{
                structure.typeStructure.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="structure.id"
          :to="{ name: 'StructureEdit', params: { structureId: structure.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./structure-details.component.ts"></script>
