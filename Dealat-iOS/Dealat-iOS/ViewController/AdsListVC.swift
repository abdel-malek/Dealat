//
//  AdsListVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import CHIPageControl

class AdsListVC: BaseVC {
    
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var collectionView2 : UICollectionView!
    @IBOutlet weak var pageControl : CHIPageControlAji!
    
    @IBOutlet weak var markImg: UIBarButtonItem!
    @IBOutlet weak var categoryImg: UIBarButtonItem!
    @IBOutlet weak var categoryNameLbl: UIBarButtonItem!
    
    @IBOutlet weak var viewBtn: UIBarButtonItem!
    
    
    var pageNumber = 1
    var isDataLoading : Bool = false
    var isAllDataFetched : Bool = false
    let indicater : UIActivityIndicatorView = UIActivityIndicatorView(style: .gray)
    
    
    var x1 = -1
    var timer : Timer = Timer()
    var viewsType : Int = 1
    
    var fromBookmark : Bool = false
    var fromFilter : Bool = false
    var filter : FilterParams!
    var query = String()
    var bookmark : UserBookmark!
    
    
    var commericals : [Commercial] = [Commercial]()
    
    
    var cat = Cat()
    var ads : [AD] = [AD]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        
        
        self.setupFilterButton()
        
        Provider.setScreenName("ViewAdsActivity")
    }
    
    func setupFilterButton(){
        let btn = UIButton.init()
        let image = #imageLiteral(resourceName: "filter-tool-black-shape").resized(toWidth: 15)?.withRenderingMode(.alwaysTemplate)
        btn.setImage(image, for: UIControl.State.normal)
        btn.setTitle("Filter".localized, for: UIControl.State.normal)
        btn.setTitleColor(Theme.Color.red, for: UIControl.State.normal)
        btn.titleLabel?.font = Theme.Font.Calibri.withSize(16)
        btn.contentEdgeInsets = UIEdgeInsets.init(top: 8, left: 8, bottom: 8, right: 8)
        btn.tintColor = Theme.Color.red
        btn.backgroundColor = .white
        btn.cornerRadius = 10
        if Provider.isArabic{
            btn.imageEdgeInsets.right = -8
            btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            btn.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            btn.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        }else{
            btn.imageEdgeInsets.left = -8
        }
        btn.addTarget(self, action: #selector(self.openFilter), for: UIControl.Event.touchUpInside)
        let barButton = UIBarButtonItem.init(customView: btn)
        self.navigationItem.rightBarButtonItem = barButton
    }
    
    
    override func getRefreshing() {
        self.isDataLoading = false
        self.isAllDataFetched = false
        self.pageNumber = 1
        
        getRefreshing2()
    }
    
    func getRefreshing2(){
        
        if self.fromBookmark{
            Communication.shared.get_bookmark_search(page_num : self.pageNumber,user_bookmark_id: bookmark.user_bookmark_id.intValue, callback: { (commercials,ads) in
                self.hideLoading()
                self.onDataRecived(ads)
                self.refreshTopAds(commercials)
                self.categoryNameLbl.title = nil
                self.categoryImg.image = nil
                self.markImg.image = nil
            })
        }else{
            if !self.fromFilter && self.query.isEmpty{
                Communication.shared.get_ads_by_main_category(self.cat.category_id.intValue,page_num : self.pageNumber) { (ads,commercials) in
                    self.hideLoading()
                    self.onDataRecived(ads)
                    self.refreshTopAds(commercials)
                }
            }else{
                Communication.shared.search(page_num : self.pageNumber,query: self.query, filter : Provider.filter, callback: { (ads,commercials) in
                    self.hideLoading()
                    self.onDataRecived(ads)
                    
                    if ads.isEmpty {self.collectionView2.EmptyMessage()} else {self.collectionView2.backgroundView = nil}
                    
                    self.categoryNameLbl.title = nil
                    self.categoryImg.image = nil
                    self.markImg.image = #imageLiteral(resourceName: "star_copy")
                    self.markImg.target = self
                    self.markImg.action = #selector(self.markAction)
                    self.refreshTopAds(commercials)
                })
            }
        }
    }
    
    
    func onDataRecived(_ res : [AD]){
        self.hideLoading()
        self.collectionView2.isHidden = false
        isDataLoading = false
        
        
        if self.pageNumber == 1{
            self.ads = res
        }else{
            self.ads = self.ads + res
        }
        
        if (res.count < Provider.PAGE_SIZE)
        {
            isAllDataFetched = true
        }
        
        self.collectionView2.reloadData()
        
        if !self.ads.isEmpty, self.pageNumber == 1{
            self.collectionView2.scrollToItem(at: IndexPath.init(row: 0, section: 0), at: .top, animated: false)
        }
    }
    
    func loadItems()
    {
        
        if(isDataLoading || isAllDataFetched)
        {
            return
        }
        
        isDataLoading = true
        pageNumber = pageNumber + 1
        
        indicater.startAnimating()
        indicater.hidesWhenStopped = true
        indicater.frame.size.height = 40
        
        getRefreshing2()
    }
    
    func refreshTopAds(_ commercials : [Commercial] = [Commercial]() ){
        
        if pageNumber == 1{
            self.commericals = commercials
            if commercials.isEmpty{
                let im = UIImageView.init(image: #imageLiteral(resourceName: "add_images"))
                im.contentMode = .scaleAspectFit
                self.collectionView.backgroundView = im
            }else{
                self.collectionView.backgroundView = nil
                self.pageControl.numberOfPages = self.commericals.count
                self.timer.invalidate()
                self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                self.timer.fire()
                
            }
            self.collectionView.reloadData()
        }
        
    }
    
    @objc func markAction(){
        
        let alert = UIAlertController.init(title: "AddToBookmark".localized, message: nil, preferredStyle: .alert)
        alert.addAction(UIAlertAction.init(title: "OK".localized, style: UIAlertAction.Style.default, handler: { (ac) in
            self.showLoading()
            Communication.shared.mark_search { (true) in
                self.hideLoading()
                self.markImg.image = nil
                self.showErrorMessage(text: "SearchSaved".localized)
            }
        }))
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    override func setupViews() {
        // CollectionView
        collectionView.dataSource = self
        collectionView.delegate = self
        collectionView2.dataSource = self
        collectionView2.delegate = self
        collectionView2.addSubview(ref)
//        ref.layer.zPosition = 10
//        ref.bounds = CGRect.init(x: ref.bounds.origin.x, y: -50, width: ref.bounds.size.width, height: ref.bounds.size.height)

        
        
        // Search bar
        self.setupSearchBar()
        
        // TODO
        
        if self.fromBookmark{
            self.categoryNameLbl.title = nil
            self.categoryImg.image = nil
            self.markImg.image = nil

        }else{
            if self.fromFilter || !self.query.isEmpty{
                self.searchBar.text = self.query
                self.markImg.image = nil
            }else{
                self.categoryNameLbl.title = self.cat.category_name
                self.categoryImg.image = UIImage.init(named: "cat\(self.cat.tamplate_id.stringValue)")?.withRenderingMode(UIImage.RenderingMode.alwaysOriginal)
            }
        }
    }
    
    
    @IBAction func changeView(_ sender  : UIBarButtonItem){
        self.viewsType = (viewsType == 1) ? 2 : (viewsType == 2) ? 3 : 1
        
        viewBtn.image = UIImage.init(named: "views-\(viewsType)")
        
        UIView.transition(with: self.collectionView2, duration: 0.3, options: [.showHideTransitionViews,.allowAnimatedContent], animations: {
            self.collectionView2.performBatchUpdates({
                self.collectionView2.reloadSections(IndexSet.init(integer: IndexSet.Element.init(0)))
            }, completion: nil)
        }, completion: nil)
        
        
    }
    
    @objc func rotate(){
        let cnt = self.commericals.count
        
        if(cnt > 1){
            if self.x1 != -1 {
                x1 = self.pageControl.currentPage + 1
            }else{
                self.x1 = 0
            }
        }
        else if (cnt == 1 || x1 > cnt - 1) {
            x1 = 0;
        }
        
        if cnt == 0 {
            return
        }
        
        let u = x1 % cnt
        
        self.collectionView.setContentOffset(CGPoint(x: self.collectionView.frame.size.width * CGFloat(u), y: 0) , animated: true)
    }
    
    
    override func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        
        if let searchBarText = searchBar.text {
            self.query = searchBarText
            //            Provider.searchText = searchBarText
            self.getData()
        }
    }
    
    
    @objc @IBAction func openFilter() {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "FilterBaseVC") as! FilterBaseVC
        vc.adsList = self
        
        var c : Cat! = nil
        if self.cat.tamplate_id != nil{
            c = self.cat
        }
        if Provider.filter.category != nil{
            c = Provider.filter.category
        }
        
        Provider.filter = FilterParams()
        Provider.filter.category = c
        
        let nv = UINavigationController.init(rootViewController: vc)
        self.present(nv, animated: true, completion: nil)
    }
    
}


extension AdsListVC : UICollectionViewDelegate, UICollectionViewDataSource,UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView == self.collectionView{
            return commericals.count
        }else{
            return ads.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if collectionView == self.collectionView{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
            cell.commercial = self.commericals[indexPath.row]
            return cell
        }else{
            
            var identifier = "cell-\(self.viewsType)"
            
            if self.viewsType == 3{
                identifier = "cell-\((indexPath.row % 2 == 0) ? 3 : 4)"
            }
            
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: identifier, for: indexPath) as! AdCell
            
            //check if it is the last item at the list
            if(indexPath.row == self.ads.count - 1)
            {
                loadItems()
            }
            
            cell.ad = self.ads[indexPath.row]
            return cell
        }
    }
    
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if collectionView == self.collectionView{
            if let urlString = self.commericals[indexPath.row].ad_url, let url = URL.init(string: urlString){
                if UIApplication.shared.canOpenURL(url){
                    UIApplication.shared.openURL(url)
                }
            }
        }
            
        else if collectionView == self.collectionView2{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
            vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
            vc.ad = self.ads[indexPath.row]
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        if collectionView == self.collectionView{
            return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
        }else{
            if self.viewsType == 1{
                return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width * 2 / 3)
            }else if self.viewsType == 2{
                return CGSize.init(width: collectionView.frame.width / 2, height: UIScreen.main.bounds.width * 3 / 4)
            }else{
                return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width * 477 / 1128)
            }
        }
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView{
            if self.commericals.count > 0 {
                if self.collectionView.visibleCells.count > 0{
                    let pp = round(self.collectionView.contentOffset.x / self.collectionView.frame.size.width)
                    self.pageControl.set(progress: Int(pp), animated: true)
                    
                    self.timer.invalidate()
                    self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                }
            }
        }
    }
    
}
