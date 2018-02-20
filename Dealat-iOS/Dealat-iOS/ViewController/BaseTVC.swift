//
//  BaseTVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import JGProgressHUD
import KSToastView

class BaseTVC: UITableViewController,UISearchBarDelegate {

    var hud = JGProgressHUD.init(style: JGProgressHUDStyle.extraLight)
    var ref = UIRefreshControl()
    var searchBar:UISearchBar = UISearchBar(frame: CGRect.init(x : 0,y : 0,width : 200,height : 20))

    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.endEditing(true)
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(self.onTouch))
        tap.cancelsTouchesInView = false
        self.view.addGestureRecognizer(tap)
        
        self.ref.addTarget(self, action: #selector(getRefreshing), for: .valueChanged)

    }
    
    @objc func getRefreshing(){
        self.hideLoading()
        
    }
    
    func getData(){
        self.showLoading()
        getRefreshing()
    }

    
    func showLoading(){
        hud.show(in: self.navigationController!.view, animated: false)
        hud.shadow = JGProgressHUDShadow(color: Theme.Color.red, offset: .zero, radius: 5.0, opacity: 0.2)
    }
    
    func hideLoading(){
        self.hud.dismiss(animated : false)
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        unregisterReceivers()
        registerReceivers()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        unregisterReceivers()
    }
    
    
    func registerReceivers()
    {
        notific.addObserver(self, selector: #selector(self.connectionErrorNotificationReceived(_:)), name: _ConnectionErrorNotification.not, object: nil)
        
        notific.addObserver(self, selector: #selector(self.RequestErrorNotificationRecived(_:)), name: _RequestErrorNotificationReceived.not, object: nil)
        
    }
    
    func unregisterReceivers()
    {
        notific.removeObserver(self, name: _ConnectionErrorNotification.not, object: nil)
        notific.removeObserver(self, name: _RequestErrorNotificationReceived.not, object: nil)
    }
    
    
    @objc func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.hideLoading()
        
        print("connectionErrorNotificationReceived")
        if let msg = notification.object as? String{
            self.showErrorMessage(text : msg)
        }
        if let err = notification.object as? Error{
            self.showErrorMessage(text : err.localizedDescription)
        }
    }
    
    @objc func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
        print("RequestErrorNotificationRecived")
        self.hideLoading()
        
        
        if let msg = notification.object as? String
        {
            self.showErrorMessage(text: msg)
        }
    }
    
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text, duration: 3)
    }

    @objc func onTouch() {
        self.view.endEditing(true)
    }

    func setupSearchBar(){
        self.searchBar.placeholder = "Search".localized
        self.searchBar.change(Theme.Font.Calibri)
        self.searchBar.sizeToFit()
        self.navigationItem.titleView = searchBar
        self.searchBar.delegate = self
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        
    }

    
}
