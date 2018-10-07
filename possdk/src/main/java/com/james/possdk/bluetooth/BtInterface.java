package com.james.possdk.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Created by James on 2018/10/6.
 */
public interface BtInterface {

    /**
     * start discovery bt device
     *
     * @see BluetoothAdapter.ACTION_DISCOVERY_STARTED
     */
    void btStartDiscovery(Intent intent);

    /**
     * finish discovery bt device
     *
     * @see BluetoothAdapter.ACTION_DISCOVERY_FINISHED
     */
    void btFinishDiscovery(Intent intent);

    /**
     * bluetooth status changed
     *
     * @see BluetoothAdapter.ACTION_STATE_CHANGED
     */
    void btStatusChanged(Intent intent);

    /**
     * found bt device
     *
     * @see BluetoothDevice.ACTION_FOUND
     */
    void btFoundDevice(Intent intent);

    /**
     * device bond status change
     *
     * @see BluetoothDevice.ACTION_BOND_STATE_CHANGED
     */
    void btBondStatusChange(Intent intent);

    /**
     * pairing bluetooth request
     *
     * @see BluetoothDevice.ACTION_PAIRING_REQUEST
     */
    void btPairingRequest(Intent intent);

}
