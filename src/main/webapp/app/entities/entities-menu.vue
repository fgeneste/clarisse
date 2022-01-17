<template>
  <div>
    <b-dropdown-item to="/personne">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.personne')">Personne</span>
    </b-dropdown-item>
    <b-dropdown-item to="/nuance-politique">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.nuancePolitique')">Nuance Politique</span>
    </b-dropdown-item>
    <b-dropdown-item to="/noms">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.noms')">Noms</span>
    </b-dropdown-item>
    <b-dropdown-item to="/appartenance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.appartenance')">Appartenance</span>
    </b-dropdown-item>
    <b-dropdown-item to="/structure">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.structure')">Structure</span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-structure">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.typeStructure')">Type Structure</span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-appartenance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.typeAppartenance')">Type Appartenance</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
